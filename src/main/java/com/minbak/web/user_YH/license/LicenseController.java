package com.minbak.web.user_YH.license;

import com.minbak.web.file_upload.FileService;
import com.minbak.web.file_upload.ImageFileDto;
import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.users.HostDto;
import com.minbak.web.users.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/host/license")
public class LicenseController {

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    @Autowired
    FileService fileService;

    @Autowired
    LicenseService licenseService;

    @Autowired
    UsersMapper usersMapper;

    @GetMapping
    public String hostLicensePage(){
        return "host-pages/business_license";
    }

    @PostMapping("/add")
    public String getHostLicense(@ModelAttribute LicenseDto licenseDto,
                                 @RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal CustomUserDetails userDetails){

        if (file == null) {
            redirectAttributes.addFlashAttribute("message", "첨부한 파일이 없습니다.");
            return "redirect:/host/license/add";
        }

        int hostId = licenseService.getHostIdByUserId(userDetails.getUserId());

        licenseDto.setHostId(hostId);
        licenseService.addBusinessLicense(licenseDto);
        try {
            ImageFileDto imageFile = fileService.saveFile(file,hostId,"license",userDetails.getUserId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HostDto hostDto = new HostDto();
        hostDto.setHostId(hostId);
        hostDto.setIsVerified("검증 중");
        usersMapper.updateHost(hostDto);

        redirectAttributes.addFlashAttribute("message", "영업신고증이 제출되었습니다.");
        //호스트메인페이지로 다이렉트
        return "redirect:/host/today";
    }

}
