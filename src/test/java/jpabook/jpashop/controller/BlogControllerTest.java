package jpabook.jpashop.controller;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.Blog;
import jpabook.jpashop.domain.BlogForm;
import jpabook.jpashop.repository.BlogRepository;
import jpabook.jpashop.service.BlogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogService blogService;

    private MockHttpSession session;
    private MockMvc mvc;

    @Test
    public void 글_작성() throws Exception{
        //given
        Member member = new Member();
        member.setName("userAB");
        member.setPass("123123");
        member.setId(1l);

        BlogForm form = new BlogForm();
        form.setTitle("title1");
        form.setMemberId(member.getId());
        form.setMemberName(member.getName());
        form.setContent("content1");



        //when
        blogService.create(form, member.getId());
//        blogRepository.create(blog);

        //then
        List<Blog> blogs = blogRepository.findAll();

    }
    @Test
    public void 글_수정() throws Exception{
        //given
        Member member = new Member();
        member.setName("userAB");
        member.setPass("123123");
        member.setId(1l);

        BlogForm form = new BlogForm();
        form.setTitle("title1");
        form.setMemberId(member.getId());
        form.setMemberName(member.getName());
        form.setContent("content1");
        form.setBlogCategory(BlogCategory.Java);

        Long blogId = blogService.create(form, member.getId());

        BlogForm form2 = new BlogForm();
        form2.setTitle("updateTitle");
        form2.setMemberId(member.getId());
        form2.setMemberName(member.getName());
        form2.setContent("updateContent");
        form2.setBlogCategory(BlogCategory.NetWork);

        //when
        blogService.update(blogId, form2.getTitle(), form2.getBlogCategory(), form2.getContent());

        //then
        List<Blog> result = blogService.findAll();

        assertThat(result.get(0).getTitle()).isEqualTo(form2.getTitle());
        assertThat(result.get(0).getBlogId()).isEqualTo(blogId);
        assertThat(result.get(0).getContent()).isEqualTo(form2.getContent());
        assertThat(result.get(0).getBlogCategory()).isEqualTo(form2.getBlogCategory());

    }



}