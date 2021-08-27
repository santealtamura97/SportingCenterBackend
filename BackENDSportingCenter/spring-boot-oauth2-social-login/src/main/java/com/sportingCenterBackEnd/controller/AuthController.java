package com.sportingCenterBackEnd.controller;

import javax.validation.Valid;

import com.sportingCenterBackEnd.config.CurrentUser;
import com.sportingCenterBackEnd.dto.*;
import com.sportingCenterBackEnd.model.ConfirmationToken;
import com.sportingCenterBackEnd.model.Role;
import com.sportingCenterBackEnd.model.User;
import com.sportingCenterBackEnd.model.UserCode;
import com.sportingCenterBackEnd.repo.ConfirmationTokenRepository;
import com.sportingCenterBackEnd.repo.UserCodeRepository;
import com.sportingCenterBackEnd.repo.UserRepository;
import com.sportingCenterBackEnd.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.sportingCenterBackEnd.exception.UserAlreadyExistAuthenticationException;
import com.sportingCenterBackEnd.security.jwt.TokenProvider;
import com.sportingCenterBackEnd.service.UserService;
import com.sportingCenterBackEnd.util.GeneralUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@EnableGlobalAuthentication
public class AuthController {

	private final UserRepository userRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	TokenProvider tokenProvider;

	@Autowired
	private final ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private final UserCodeRepository userCodeRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	public AuthController(UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository, UserCodeRepository userCodeRepository) {
		this.userRepository = userRepository;
		this.confirmationTokenRepository = confirmationTokenRepository;
		this.userCodeRepository = userCodeRepository;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(authentication);
		LocalUser localUser = (LocalUser) authentication.getPrincipal();
		JwtAuthenticationResponse response = new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser));
		return ResponseEntity.ok(response);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {
			User user = userService.buildUser(signUpRequest);
			ConfirmationToken confirmationToken = new ConfirmationToken(user);
			confirmationTokenRepository.save(confirmationToken);
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Complete Registration!");
			mailMessage.setText("To confirm your account, please click here : "
					+"http://localhost:8080/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());

			emailSenderService.sendEmail(mailMessage);
		} catch (UserAlreadyExistAuthenticationException e) {
			return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(new ApiResponse(true, "OK"));
	}

	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> confirmUserAccount( @RequestParam("token")String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		if(token != null) {
			User user = token.getUser();
			user.setEnabled(true);
			userRepository.save(user);
			confirmationTokenRepository.delete(token);
			return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
		}
		else {
			return ResponseEntity.ok().body(new ApiResponse(false, "Errore"));
		}
	}

	@RequestMapping(value = "/{authToken}", method = RequestMethod.GET)
	public ResponseEntity<String> check_token(@PathVariable("authToken") String token) {
		token = token.substring(7, token.length());
		if(tokenProvider.validateToken(token)){
			System.out.println("token okkkk");
			Long userIdFromToken = tokenProvider.getUserIdFromToken(token);
			User user = userService.findUserById(userIdFromToken).orElseThrow();
			List<String> rolesList = new ArrayList<>();
			String userRoles = new String();
			for (Role role: user.getRoles()) {
				rolesList.add(role.getName());
				userRoles = userRoles + role.getName() + " ";
			}
			UserInfo userInfo = new UserInfo(user.getId().toString(),user.getDisplayName(),user.getEmail(),rolesList, user.getAbbonamento());
			return ResponseEntity.ok()
					//.headers(responseHeaders)
					.body(userRoles);
		}else {
			return null;
		}
	}

	@PostMapping("/usercode")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserCode userCode) {
		userCodeRepository.save(userCode);
		return ResponseEntity.ok(new ApiResponse(true, "OK"));
	}

	@PostMapping( "/validateUserCode")
	public ResponseEntity<?> validateUserCode(@Valid @RequestBody String userCode) {
		userCode = userCode.replaceAll("^\"+|\"+$", "");
		System.out.println(userCode);
		UserCode uC = userCodeRepository.findByCode(userCode);
		UserCodeResponse userCodeResponse = new UserCodeResponse(uC.getDisplay_name(), uC.getId_abbonamento(), uC.getScadenzaAbbonamento(), uC.getIngressi());
		return ResponseEntity.ok(userCodeResponse);
	}

	/*@PostMapping( "/profileImage")*/
	@RequestMapping(value = "/profileImage", method = RequestMethod.POST)
	public ResponseEntity<?> uploadProfileImage(@RequestPart(name = "img") MultipartFile img) {
		System.out.println("Request  update photo "+ img.getOriginalFilename());
		return ResponseEntity.ok().body(new ApiResponse(true, "Foto profilo caricata correttamente!"));
	}

	@GetMapping("/authenticateToken")
	public ResponseEntity<?> authenticateToken(@CurrentUser LocalUser user) {
		return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
	}

	@PostMapping("/modify")
	public ResponseEntity<?> modifyUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {
			userService.modificaUserEsistente(signUpRequest);
		} catch (UserAlreadyExistAuthenticationException e) {
			return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().body(new ApiResponse(true, "Utente modificato correttamente"));
	}

	@RequestMapping(value = "SubIdByUserId/{userId}", method = RequestMethod.GET)
	public String subIdByUserId(@PathVariable("userId") Long userId){
		Optional<User> user = userRepository.findById(userId);
		User user1 = user.get();
		return user1.getAbbonamento();
	}

	@RequestMapping(value = "userbyid/{userId}", method = RequestMethod.GET)
	public User userById(@PathVariable("userId") Long userId) {
		Optional<User> user = userRepository.findById(userId);
		return user.get();
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> usersByIds(){
		return (List<User>) userRepository.findAll();
	}
}
