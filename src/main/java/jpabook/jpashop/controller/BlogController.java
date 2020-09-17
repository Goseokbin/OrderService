package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Blog;
import jpabook.jpashop.domain.BlogCategory;
import jpabook.jpashop.domain.BlogForm;
import jpabook.jpashop.service.BlogService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final MemberService memberService;


    @GetMapping(value="/blog/new")
    public String BlogForm(Model model) {
        model.addAttribute("blogForm", new BlogForm());
        model.addAttribute("blogCategory", BlogCategory.values());
        model.addAttribute("Datetime", LocalDateTime.now());
        return "blog/createBlogForm";
    }

    @PostMapping(value="/blog/new")
    public String create(@Valid BlogForm form,HttpSession session){
        Long userId =(Long)session.getAttribute("userId");
        blogService.create(form,userId);
        return "redirect:/";
    }
    @GetMapping(value="/blog")
    public String list(Model model){
        List<Blog> blogs = blogService.findAll();
        model.addAttribute("blogs", blogs);
        return "blog/blogList";
    }

    @GetMapping(value = "/blog/{blogId}/update")
    public String update(@PathVariable Long blogId,Model model){
        Blog blog = blogService.findbyId(blogId);
        model.addAttribute("blog",blog);
        model.addAttribute("blogId", blogId);
        return "blog/updateBlogForm";
    }
    @PutMapping(value = "/blog/modify")
    public String modify(@RequestBody BlogForm blogForm, @PathVariable Long blogId) {
        blogService.update(blogId, blogForm.getTitle(), blogForm.getBlogCategory(), blogForm.getContent());
        return "blog/blogList";
    }


}
