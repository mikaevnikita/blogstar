package ru.mikaev.blogstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.mikaev.blogstar.dao.UsersRepository;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.forms.ChangePasswordForm;
import ru.mikaev.blogstar.forms.ChangeProfileForm;
import ru.mikaev.blogstar.security.UserDetailsImpl;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.utils.ControllerUtils;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@PropertySources({
        @PropertySource("classpath:ru/mikaev/blogstar/application.properties")
})
public class ProfileController {

    @Autowired
    private UsersRepository usersRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/user/profile")
    String profile(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto userDto = UserDto.fromUser(userDetails.getUser());
        model.addAttribute("user", userDto);
        return "/user/profile/profile";
    }

    @GetMapping("/user/profile/changeProfile")
    String showChangeProfilePage(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto userDto = UserDto.fromUser(userDetails.getUser());

        model.addAttribute("user", userDto);
        return "/user/profile/changeProfile";
    }

    @PostMapping("/user/profile")
    String changeProfileInfo(Authentication authentication, @Valid ChangeProfileForm form, BindingResult bindingResult, Model model) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            //setting wrong fields
            UserDto userDto = UserDto
                    .builder()
                    .firstName(form.getFirstName())
                    .lastName(form.getLastName())
                    .profilePhotoFilename(user.getProfilePhotoFilename())
                    .build();
            model.addAttribute("user", userDto);
            return "/user/profile/changeProfile";
        }

        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());


        MultipartFile profilePhoto = form.getProfilePhoto();
        if(profilePhoto != null && !profilePhoto.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + profilePhoto.getOriginalFilename();
            user.setProfilePhotoFilename(resultFilename);
            profilePhoto.transferTo(new File(uploadPath + "/" + resultFilename));
        }

        usersRepository.save(user);

        return "redirect:/user/profile";
    }

}