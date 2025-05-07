package com.project.ecommerce_backend.controller;


import com.project.ecommerce_backend.config.JwtProvider;
import com.project.ecommerce_backend.exception.UserException;
import com.project.ecommerce_backend.model.Cart;
import com.project.ecommerce_backend.model.User;
import com.project.ecommerce_backend.repositry.UserRepository;
import com.project.ecommerce_backend.request.LoginRequest;
import com.project.ecommerce_backend.response.AuthResponse;
import com.project.ecommerce_backend.services.CartItemService;
import com.project.ecommerce_backend.services.CustomUserServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CustomUserServiceImplementation customUserServiceImplementation;
    private CartItemService cartItemService;
    public AuthController(UserRepository userRepository,JwtProvider jwtProvider,PasswordEncoder passwordEncoder,CustomUserServiceImplementation customUserServiceImplementation,CartItemService cartItemService) {
        this.userRepository = userRepository;
        this.jwtProvider=jwtProvider;
        this.passwordEncoder=passwordEncoder;
        this.customUserServiceImplementation=customUserServiceImplementation;
        this.cartItemService=cartItemService;

    }
     @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws UserException{
      String email=user.getEmail();
      String password= user.getPassword();;
      String firstName= user.getFirstName();
      String lastName= user.getLastName();

      User isEmailExists= userRepository.findByEmail(email);

      if(isEmailExists!=null){
          throw new UserException("Email is Already exists");
      }
      User createduser=new User();
      createduser.setEmail(email);
      createduser.setPassword(passwordEncoder.encode(password));
      createduser.setFirstName(firstName);
      createduser.setLastName(lastName);

      User savedUser=userRepository.save(createduser);
         Cart cart=cartItemService.createCart(savedUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtProvider.generateToken(authentication);

         AuthResponse authResponse=new AuthResponse();
         authResponse.setJwt(token);
         authResponse.setMessage("signup Success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);



    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){
        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();
        Authentication authentication=authenticate(username,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("signin Success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);



    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails= customUserServiceImplementation.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("invalid Username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
