package com.ljy.podo.major.infrastructure;

import java.util.List;

import com.ljy.podo.major.projection.MajorListData;
import com.ljy.podo.user.Major;

public interface MajorRepository {
	void save(Major major);
	boolean existByName(Major major);
	List<MajorListData> findAll(); 
}
