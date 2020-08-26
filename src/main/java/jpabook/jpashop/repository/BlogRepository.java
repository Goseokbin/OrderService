package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BlogRepository {
    private final EntityManager em;
    public Long create(Blog blog) {
        em.persist(blog);
        return blog.getBlogId();
    }
}
