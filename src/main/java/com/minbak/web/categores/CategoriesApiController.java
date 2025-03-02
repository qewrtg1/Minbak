package com.minbak.web.categores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/admin/categories")
public class CategoriesApiController {

    @Autowired
    CategoresService categoresService;

    @PostMapping("/reorder")
    //카테고리 순서대로 재배열
    public ResponseEntity<?> reOrderCategories(@RequestBody Map<String, List<Integer>> request){

        System.out.println("🔍 Received request: " + request);  // 콘솔에 받은 데이터 출력
        List<Integer> newOrder = request.get("order");

        // 카테고리 순서 변경 로직
        categoresService.updateCategoryOrder(newOrder);

        // 순서 변경이 완료되었음을 알리는 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "카테고리 순서가 변경되었습니다.");

        //response와 200반환
        return ResponseEntity.ok(response);

    }
}
