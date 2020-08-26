package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Blog;
import jpabook.jpashop.domain.BlogForm;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.BlogService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final MemberService memberService;


    @GetMapping("/blog/new")
    public String BlogForm(Model model) {
        model.addAttribute("blogForm", new BlogForm());
        return "blog/createBlogForm";
    }
    @PostMapping("/blog/new")
    public String create(@Valid BlogForm form, BindingResult result){
        if(result.hasErrors()){
            return "blog/createBlogForm";
        }
        Blog blog = new Blog();
        blog.setTittle(form.getTitle());
        Member member = memberService.findOne(form.getMemberId());
        blog.setMember(member);
        blog.setLocalDateTime(form.getLocalDateTime());
        blog.setBlogCategory(form.getBlogCategory());

        blogService.create(blog);
        return "redirect:/";
}
}
