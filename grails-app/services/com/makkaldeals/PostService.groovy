package com.makkaldeals

import com.makkaldeals.auth.Customer
import grails.orm.PagedResultList

class PostService {

  def emailService;
  def springSecurityService;

  static transactional = false;

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
    params.max = params.max ?: 1;
    def criteria = Post.createCriteria();
    PagedResultList  results =  criteria.list(max:params.max, offset:params.offset) {
      eq 'author', _author
      eq 'published', true
      order "dateCreated", "desc"
      cache true
    }

    return results;
  }

  public List<Post> findPostsByTag(String tag) {
    //def posts = Post.findAllByTag(params.tag.trim(), [max: 10, offset: params.offset, sort: "dateCreated", order: "desc"])
    def posts = Post.findAllByTag(tag.trim(), [sort: "dateCreated", order: "desc"])
    return posts.findAll { it.published };

  }

  public List<Post> findPostsByBusiness(Map params){
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

    //TODO implement pagination
    List<Post> posts = Post.withCriteria {

      author {
        'in'('business', business)
      }
      eq 'published', true
      order "dateCreated", "desc"
      cache true
    }

    return posts;
  }
}
