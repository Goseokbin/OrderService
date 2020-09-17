package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlogRepository {
    private final EntityManager em;
    @Transactional
    public Long create(Blog blog) {
        em.persist(blog);
        return blog.getBlogId();
    }

    public List<Blog> findAll() {
        return em.createQuery("select b from Blog b ").getResultList();
    }

    public Blog findbyId(Long blogId) {
       return em.find(Blog.class,blogId);
    }


    public void update(Blog blog) {
        em.merge(blog);
    }
}
