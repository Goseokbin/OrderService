package jpabook.jpashop.service;

import jpabook.jpashop.domain.Blog;
import jpabook.jpashop.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    public Long create(Blog blog) {
        blogRepository.create(blog);
        return blog.getBlogId();
    }
}
