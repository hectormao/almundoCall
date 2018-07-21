package com.almundo.call.dom.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.almundo.call.dom.ent.Call;
/**
 * Mongo repository to access to Call document in MongoDB
 * @author hectormao
 *
 */
public interface CallRepository extends MongoRepository<Call, String> {

}
