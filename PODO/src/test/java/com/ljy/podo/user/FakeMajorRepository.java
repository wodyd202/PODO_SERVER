package com.ljy.podo.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ljy.podo.major.infrastructure.MajorRepository;
import com.ljy.podo.major.projection.MajorListData;

public class FakeMajorRepository implements MajorRepository {

	private final Map<String, Major> repository = new HashMap<>();

	@Override
	public boolean existByName(Major major) {
		return repository.get(major.toString()) != null;
	}

	@Override
	public void save(Major major) {
		repository.put(major.toString(), major);
	}

	@Override
	public List<MajorListData> findAll() {
		return null;
	}

}
