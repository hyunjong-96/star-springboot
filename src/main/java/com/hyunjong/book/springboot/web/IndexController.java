package com.hyunjong.book.springboot.web;

import com.hyunjong.book.springboot.config.auth.LoginUser;
import com.hyunjong.book.springboot.config.auth.dto.SessionUser;
import com.hyunjong.book.springboot.service.PostsService;
import com.hyunjong.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts",postsService.findAllDesc()); //model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다. model.addAttribute(key,value)

        //SessionUser user = (SessionUser)httpSession.getAttribute("user"); //CustomOAuth2UserService에서 로그인 성공시 세션에 SessionUser를 넣는다.(로그인 성공시 (SessionUser)httpSession.getAttribute("user")에서 값을 가져올수 있다)
        if(user != null){   //session에 저장된 값이 있는 경우에만 model에 userName으로 등록한다. 세션에 저장된 값이 없으면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이게 된다.
            model.addAttribute("userName",user.getName());
        }
        return "index"; //model에 저장된 객체들을 index로 전달해준다.
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "postssave";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto); //postsupdate라는 view에서 model을 통해 객체를 사용할수 있게 해주는데 post에 PostsResponseDto에 있는 필드들을 저장한 객체를 저장하고 postsupdate에서 posts의 명으로 사용할수있게된다.
        return "postsupdate";
    }

    @GetMapping("/fileUpload")
    public String fildUpload(){return "file";}


}
