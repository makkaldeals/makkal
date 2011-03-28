package com.grepdeals

import com.grepdeals.auth.Customer
import grails.orm.PagedResultList
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.springframework.beans.factory.InitializingBean

class PostService implements InitializingBean {

  def emailService;
  def springSecurityService;
  int maxPostsPerPage;

  static transactional = false;

  void afterPropertiesSet() {
    maxPostsPerPage = CH.config.grepdeals.posts.max.per.page;
  }

  public Post create(Map params) {
    def post = Post.get(params.id) ?: new Post()
    post.properties = params['post']
    post.author = springSecurityService.currentUser;
    post.published = true
    if (post.save()) {
      post.parseTags(params.tags, ",")
      emailService.sendPostConfirmation(post);
    }
    return post;

  }

  public PagedResultList findPostsByAuthor(Map params) {
    def _author = params.author ? Customer.findByEmail(params.author) : springSecurityService.currentUser;
    params.max = params.max ?: maxPostsPerPage;
    def criteria = Post.createCriteria();
    PagedResultList results = criteria.list(max: params.max, offset: params.offset) {
      eq 'author', _author
      eq 'published', true
      order "dateCreated", "desc"
      cache true
    }

    return results;
  }

  public Map findPostsByTag(Map params) {
    //def posts = Post.findAllByTag(params.tag.trim(), [max: 10, offset: params.offset, sort: "dateCreated", order: "desc"])
    //def posts = Post.findAllByTag(tag.trim(), [sort: "dateCreated", order: "desc"])

    params.max = params.max ? Integer.parseInt(params.max) : maxPostsPerPage;
    params.offset = params.offset ? Integer.parseInt(params.offset) : 0;
    String tag = params.tag.trim();
    def posts = Post.findAllByTagWithCriteria(tag) {
      eq 'published', true
      order "dateCreated", "desc"
      maxResults params.max
      firstResult params.offset

    }
    Map results = [:];
    results.list = posts;

    String countByTagHQL = """
       SELECT count(*)
       FROM Post post
                ,TagLink tagLink
       WHERE post.id = tagLink.tagRef
       AND tagLink.type = 'post'
       AND tagLink.tag.name = :tag
       AND post.published = true """

    results.totalCount = Post.executeQuery(countByTagHQL, [tag: tag]).get(0);

    return results;

  }

  public PagedResultList findPostsByBusiness(Map params) {
    //TODO: Look for better way to do this query

    params.max = params.max ?: maxPostsPerPage;
    params.offset = params.offset ?: 0;

    def business;
    def city = params.city;
    def areaCode = params.areaCode;

    if (!city && !areaCode) {
      business = Business.findAllByName(params.business);
    }
    else if (city && areaCode) {
      business = Business.withCriteria {
        ilike 'city', city
        eq 'areaCode', areaCode
      }
    } else if (city) {
      business = Business.findAllByNameAndCityIlike(params.business, city)
    } else if (areaCode) {
      business = Business.findAllByNameAndAreaCode(params.business, areaCode);
    }


    def criteria = Post.createCriteria();
    PagedResultList results = criteria.list(max: params.max, offset: params.offset) {
      author {
        'in'('business', business)
      }
      eq 'published', true
      order "dateCreated", "desc"
      cache true
    }


    return results;
  }
}
