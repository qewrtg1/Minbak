package com.minbak.web.users;

import com.minbak.web.categores.CategoryDto;
import com.minbak.web.categores.OptionDto;
import com.minbak.web.common.dto.PageDto;
import com.minbak.web.file_upload.FileMapper;
import com.minbak.web.file_upload.FileService;
import com.minbak.web.file_upload.ImageFileDto;
import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.user_YH.license.LicenseDto;
import com.minbak.web.user_YH.license.LicenseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    LicenseService licenseService;

    @Autowired
    FileMapper fileMapper;

    @Autowired
    FileService fileService;

    @GetMapping("/login")
    public String loginPage() {
        return "/spring-security/login";
    }

    @GetMapping
    public String adminPage() {
        return "/spring-security/admin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "/spring-security/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            return "redirect:/signup";
        }
        // 유효성 검사 성공 시 처리
        usersService.createUser(userDto);

        return "redirect:/login";
    }


    @GetMapping("/my/info")
    public String myPage(Model model,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {

        System.out.println(userDetails.getUserId());
        //세션의 현재 사용자 id, 현 프로젝트에서는 email
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //세션의 현재 사용자의 role ROLE_USER or ROLE_ADMIN
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();
        //하나의 role만 가져오는 코드. 이코드가 아니어도 id값으로 원하는 정보를 가져오는 로직을 service에서 짜면 됨.


        model.addAttribute("id", email);
        model.addAttribute("role", role);

        return "/spring-security/mypage";
    }


    //get메서드 로그아웃 코드
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }

    @GetMapping("/clear/refresh")
    public String clearRefreshTokenData(Model model){
        try {
            usersService.deleteExpiredRefreshTokens();
        }catch (Exception e){
            model.addAttribute("message","에러남");
            return null;
        }
        return "/spring-security/home";
    }


    @GetMapping("/users/delete/{id}")
    public String userDelete(@PathVariable("id") int userId){

        usersService.deleteUserByUserId(userId);

        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String userEditPage(@PathVariable("id") int userId,Model model){

        model.addAttribute("userDto",usersService.findUserByUserId(userId));

        return "/users/edit";
    }


//여기서부터 어드민페이지 진짜구현

    @GetMapping("/users")
    //어드민 유저페이지 페이지네이션
    public String adminUserMainPage(@RequestParam(name ="page", defaultValue = "1")int page,
                                    @RequestParam(name ="size", defaultValue = "10")int size,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String email,
                                    @RequestParam(required = false) Boolean enabled,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                    @RequestParam(required = false) Integer bookCount,
                                    Model model){

        //페이지에 보여줄 유저 정보 가져오기
        PageDto<UserResponseDto> userPageDto = usersService.searchUsersWithBookCount(page, size, name, email, enabled, startDate, endDate, bookCount);

        // 모델에 데이터 전달
        model.addAttribute("userPageDto",userPageDto);

        // 검색 필드 값 모델에 추가
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("enabled", enabled);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("bookCount", bookCount);

        return "/users/main";
    }


    @GetMapping("/users/host")
    //어드민 유저페이지 페이지네이션
    public String adminUserHostPage(@RequestParam(name ="page", defaultValue = "1")int page,
                                    @RequestParam(name ="size", defaultValue = "10")int size,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String email,
                                    @RequestParam(required = false) Boolean enabled,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                    @RequestParam(required = false) Integer bookCount,
                                    @RequestParam(required = false) String isVerified,
                                    Model model){

        //페이지에 보여줄 유저 정보 가져오기
        PageDto<HostResponseDto> hostPageDto = usersService.searchHostsWithRoomCount(page, size, name, email, enabled, startDate, endDate, bookCount, isVerified);

        // 모델에 데이터 전달
        model.addAttribute("hostPageDto",hostPageDto);

        // 검색 필드 값 모델에 추가
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("enabled", enabled);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("bookCount", bookCount);
        model.addAttribute("isVerified", isVerified);

        return "/users/host";
    }

    @GetMapping("/users/detail/{id}")
    public String userDetail(@PathVariable("id") int userId,Model model){

        Integer hostId = licenseService.getHostIdByUserId(userId);

        if(hostId != null){
            LicenseDto licenseDto = licenseService.getBusinessLicenseByHostId(hostId);
            if(licenseDto != null){
                licenseDto.setLicenseFileUrl(fileMapper.findLicenseImagesUrlByHostId(hostId));
            }
            model.addAttribute("license",licenseDto);
        }


        model.addAttribute("userDto",usersService.findUserByUserId(userId));
        model.addAttribute("hostDto",usersService.findHostByUserId(userId));


        return "/users/user-detail";
    }

    @GetMapping("/users/report")
    public String userReports(@RequestParam(value = "page", defaultValue = "1") int page,       // 기본값 1
                              @RequestParam(value = "size", defaultValue = "10") int size,     // 기본값 10
                              @RequestParam(required = false) String reporterEmail,
                              @RequestParam(required = false) String reportedUserEmail,
                              @RequestParam(required = false) String reportReason,
                              @RequestParam(required = false) String status,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startReportDate,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endReportDate,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startProcessedAt,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endProcessedAt,
                              Model model){

        // 서비스 호출하여 데이터 가져오기
        PageDto<UserReportDto> pageDto = usersService.searchUserReports(
                page, size, reporterEmail, reportedUserEmail, reportReason, status,
                startReportDate, endReportDate, startProcessedAt, endProcessedAt);

        // 모델에 페이징 데이터 전달
        model.addAttribute("pageDto", pageDto);

        model.addAttribute("reporterEmail", reporterEmail);
        model.addAttribute("reportedUserEmail", reportedUserEmail);
        model.addAttribute("reportReason", reportReason);
        model.addAttribute("status", status);
        model.addAttribute("startReportDate", startReportDate);
        model.addAttribute("endReportDate", endReportDate);
        model.addAttribute("startProcessedAt", startProcessedAt);
        model.addAttribute("endProcessedAt", endProcessedAt);

        return "users/report";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute UserDto userDto, Model model) {
        try {
            usersService.updateUser(userDto);
            model.addAttribute("message", "유저 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            model.addAttribute("message", "유저 정보 수정에 실패했습니다.");
        }

        return "redirect:/admin/users/detail/" + userDto.getUserId();
    }

    @GetMapping("/users/create/host")
    public String createHostPage(@RequestParam int userId,Model model){

        model.addAttribute("userId",userId);
        return "/users/create-host";
    }

    @PostMapping("/users/create/host")
    public String registerHost(@ModelAttribute HostDto hostDto) {

        usersService.createHost(hostDto);
        usersService.makeHost(hostDto.getUserId());

        return "redirect:/admin/users/detail/" + hostDto.getUserId(); // 성공 페이지로 리다이렉트
    }

    @PostMapping("/users/hosts/update")
    public String updateHost(@ModelAttribute HostDto hostDto, Model model) {
        usersService.updateHost(hostDto);
        model.addAttribute("message", "호스트 정보가 성공적으로 수정되었습니다.");
        return "redirect:/admin/users/detail/" + hostDto.getUserId();
    }

    @GetMapping("/users/license/create")
    public String licenseCreatePage(@RequestParam int hostId,
                                    @RequestParam int userId, Model model){

        model.addAttribute("hostId",hostId);
        model.addAttribute("userDto",usersService.findUserByUserId(userId));
        return "/users/license-create";
    }

    @PostMapping("/users/license/create")
    public String registerBusinessLicense(@ModelAttribute LicenseDto licenseDto,
                                          @RequestParam("userId") int userId,
                                          @RequestParam("file") MultipartFile file,
                                          RedirectAttributes redirectAttributes) {

        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "첨부한 파일이 없습니다.");
            return "redirect:/admin/license/create";
        }

        try {
            // 파일 저장 및 파일 URL 생성
            ImageFileDto imageFile = fileService.saveFile(file, licenseDto.getHostId(), "license", userId);
            licenseDto.setLicenseFileUrl(imageFile.getFileUrl());
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }

        // 영업신고증 정보 저장
        licenseService.addBusinessLicense(licenseDto);

        // 호스트 검증 상태 업데이트 (검증 중)
        HostDto hostDto = new HostDto();
        hostDto.setHostId(licenseDto.getHostId());
        hostDto.setIsVerified("검증 중");
        usersService.updateHost(hostDto);

        redirectAttributes.addFlashAttribute("message", "호스트의 영업신고증이 등록되었습니다.");

        return "redirect:/admin/users/detail/"+userId;
    }

    @PostMapping("/users/license/delete")
    public String deleteLicense(@RequestParam("hostId") int hostId, RedirectAttributes redirectAttributes) {
        // 영업신고증 존재 여부 확인
        LicenseDto license = licenseService.getLicenseByHostId(hostId);
        if (license == null) {
            redirectAttributes.addFlashAttribute("message", "해당 호스트의 영업신고증이 존재하지 않습니다.");
            return "redirect:/admin/users";
        }

        // 영업신고증 파일 삭제
        try {
            boolean isFileDeleted = fileService.deleteFile(fileService.findLicenseImagesUrlByHostId(hostId));
//            if (!isFileDeleted) {
//                redirectAttributes.addFlashAttribute("message", "파일 삭제 중 오류가 발생했습니다.");
//                return "redirect:/admin/users";
//            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "파일 삭제 실패: " + e.getMessage());
            return "redirect:/admin/users";
        }

        // 영업신고증 삭제
        licenseService.deleteLicenseByHostId(hostId);


        // 호스트 상태를 "미검증"으로 변경
        HostDto hostDto = new HostDto();
        hostDto.setHostId(hostId);
        hostDto.setIsVerified("미검증");
        usersService.updateHost(hostDto);

        redirectAttributes.addFlashAttribute("message", "영업신고증이 삭제되었습니다.");
        return "redirect:/admin/users/detail/"+licenseService.getUserIdByHostId(hostId);
    }

    @GetMapping("/rooms/create")
    public String createRoomPage(@RequestParam("userId") int userId,Model model){
        model.addAttribute("userId",userId);
        return "/users/create-room";
    }

    @PostMapping("/rooms/create")
    public String createRoom(
            @RequestParam("userId") int userId,  // 숙소 등록한 사용자 ID
            @ModelAttribute UserRoomsDto userRoomsDto,  // 숙소 정보 DTO
            @RequestParam("roomImages") MultipartFile[] roomImages,  // 다중 파일 업로드
            RedirectAttributes redirectAttributes) {

        try {
            // ✅ 숙소 정보 저장
            userRoomsDto.setUserId(userId);
            usersService.insertRoom(userRoomsDto);

            // ✅ 이미지 파일 저장 (다중 업로드)
            for (MultipartFile file : roomImages) {
                if (!file.isEmpty()) {
                    ImageFileDto imageFile = fileService.saveFile(file, userRoomsDto.getRoomId(), "rooms", userId);
                    System.out.println("✅ 저장된 파일: " + imageFile.getFileUrl());
                }
            }

            redirectAttributes.addFlashAttribute("message", "숙소가 성공적으로 등록되었습니다.");
            
            return "redirect:/admin/rooms/"+userRoomsDto.getRoomId()+"/categories-options";  //숙소칼럼 생성 후 카테고리 및 옵션설정

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "숙소 등록 중 오류가 발생했습니다.");
            return "redirect:/admin/users";
        }
    }

    @GetMapping("/rooms/{roomId}/categories-options")
    public String showCategoryAndOptions(@PathVariable("roomId") int roomId, Model model) {
        List<CategoryDto> categories = usersService.getAllCategories();
        Map<String, List<OptionDto>> optionsByCategory = usersService.getOptionsGroupedByCategory();


        model.addAttribute("room",usersService.getRoomById(roomId));
        model.addAttribute("roomId", roomId);
        model.addAttribute("categories", categories);
        model.addAttribute("optionsByCategory", optionsByCategory);

        return "/users/categories-options"; // Thymeleaf 페이지
    }

    @PostMapping("/rooms/{roomId}/categories-options")
    public String saveCategoryAndOptions(
            @PathVariable("roomId") int roomId,
            @RequestParam(value = "categoryIds", required = false) List<Integer> categoryIds,
            @RequestParam(value = "optionIds", required = false) List<Integer> optionIds,
            RedirectAttributes redirectAttributes) {

        try {
            // 숙소의 기존 카테고리 및 옵션을 제거 후 새롭게 저장
            usersService.updateRoomCategories(roomId, categoryIds);
            usersService.updateRoomOptions(roomId, optionIds);

            redirectAttributes.addFlashAttribute("success", "숙소 카테고리 및 옵션이 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "숙소 카테고리 및 옵션 저장 중 오류가 발생했습니다.");
        }

        return "redirect:/admin/rooms/detail/" + roomId;
    }


}

