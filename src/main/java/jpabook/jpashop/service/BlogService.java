package jpabook.jpashop.service;

import jpabook.jpashop.domain.Blog;
import jpabook.jpashop.domain.BlogCategory;
import jpabook.jpashop.domain.BlogForm;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.BlogRepository;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;

    public Long create(BlogForm form, Long memberId) {
        Member member = memberRepository.findOne(memberId);

        Blog blog = new Blog().builder()
                .member(member)
                .title(form.getTitle())
                .content(form.getContent())
                .blogCategory(form.getBlogCategory())
                .build();


        blogRepository.create(blog);

        return blog.getBlogId();
    }

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }



    @Transactional
    public void update(Long blogId, String title, BlogCategory blogCategory, String content) {
        Blog blog = blogRepository.findbyId(blogId);
        blog.setContent(content);
        blog.setBlogCategory(blogCategory);
        blog.setTitle(title);
        blogRepository.update(blog);
    }

    public Blog findbyId(Long blogId) {
       return blogRepository.findbyId(blogId);
    }
}
