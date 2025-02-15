package com.example.camerashop_be.repository.specs;

import com.example.camerashop_be.entity.Filter;
import com.example.camerashop_be.entity.Role;
import com.example.camerashop_be.entity.User;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserSpecification implements Specification<User> {
	private final List<Filter> list;

	public UserSpecification() {
		this.list = new ArrayList<>();
	}

	public void add(Filter criteria) {
		list.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		List<Predicate> predicates = new ArrayList<>();


		for (Filter criteria : list) {
			switch (criteria.getOperator()){
				case IN:
					if(criteria.getValue() instanceof ArrayList){
						Date start = (Date) ((ArrayList<?>) criteria.getValue()).get(0);
						Date end = (Date) ((ArrayList<?>) criteria.getValue()).get(1);
						predicates.add(builder.between(root.get(criteria.getKey()),start,end));
					}else if(criteria.getValue() instanceof Date){

						Date start = (Date) criteria.getValue();
						predicates.add(builder.equal(root.get(criteria.getKey()).as(LocalDate.class), builder.literal(start).as(LocalDate.class)));
					}
					break;

				case LIKE:
					predicates.add(builder.like(
							root.get(criteria.getKey()), "%"+criteria.getValue().toString()+"%"));
					break;

				case GREATER_THAN:
					predicates.add(builder.greaterThan(
							root.get(criteria.getKey()), criteria.getValue().toString()));
					break;

				case LESS_THAN:
					predicates.add(builder.lessThan(
							root.get(criteria.getKey()), criteria.getValue().toString()));
					break;
				case GREATER_THAN_EQUAL:
					predicates.add(builder.greaterThanOrEqualTo(
							root.get(criteria.getKey()), criteria.getValue().toString()));
					break;
				case LESS_THAN_EQUAL:
					predicates.add(builder.lessThanOrEqualTo(
							root.get(criteria.getKey()), criteria.getValue().toString()));
					break;
				case NOT_EQUAL:
					predicates.add(builder.notEqual(
							root.get(criteria.getKey()), criteria.getValue()));
					break;
				case EQUAL:
					if(criteria.getKey().equals("role")){
						Join<User, Role> join = root.join("roles");
						predicates.add(builder.equal(
								join.get("name"), criteria.getValue().toString()));
					}
					else{
						predicates.add(builder.equal(
								root.get(criteria.getKey()), criteria.getValue().toString()));
					}

					break;

//                case MATCH:
//                    predicates.add(builder.like(
//                            builder.lower(root.get(criteria.getKey())),
//                            "%" + criteria.getValue().toString().toLowerCase() + "%"));
//                    break;
//                case MATCH_END:
//                    predicates.add(builder.like(
//                            builder.lower(root.get(criteria.getKey())),
//                            criteria.getValue().toString().toLowerCase() + "%"));
//                    break;
//                case MATCH_START:
//                    predicates.add(builder.like(
//                            builder.lower(root.get(criteria.getKey())),
//                            "%" + criteria.getValue().toString().toLowerCase()));
//                    break;
//                case IN:
//                    predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
//                    break;
//                case NOT_IN:
//                    predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
//                    break;
				default:
					throw new RuntimeException("Operation not supported yet");

			}}
		query.distinct(true);
		return builder.and(predicates.toArray(new Predicate[0]));
	}
	private String splitString(String str){
		return str.split("_")[1];
	}
}