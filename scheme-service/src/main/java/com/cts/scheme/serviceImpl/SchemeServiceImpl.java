//package com.cts.scheme.serviceImpl;
//
//
//
//import com.cts.scheme.exception.ResourceNotFoundException;
//import com.cts.scheme.model.Scheme;
//import com.cts.scheme.repository.SchemeRepository;
//import com.cts.scheme.service.SchemeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class SchemeServiceImpl implements SchemeService {
//
//    @Autowired
//    private SchemeRepository schemeRepository;
//
//    @Override
//    public List<Scheme> getAllSchemes() {
//        return schemeRepository.findAll();
//    }
//
//
//    @Override
//    public Scheme createScheme(Scheme scheme){
//        return schemeRepository.save(scheme);
//    }
//    @Override
//    public Scheme getSchemeById(Long id) {
//        return schemeRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with ID: " + id));
//    }
//}



package com.cts.scheme.serviceImpl;


import com.cts.scheme.dto.SchemeRequestDTO;
import com.cts.scheme.dto.SchemeResponseDTO;
import com.cts.scheme.exception.ResourceNotFoundException;
import com.cts.scheme.mapper.SchemeMapper;
import com.cts.scheme.model.Scheme;
import com.cts.scheme.repository.SchemeRepository;
import com.cts.scheme.service.SchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchemeServiceImpl implements SchemeService {

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private SchemeMapper schemeMapper;

    @Override
    public List<SchemeResponseDTO> getAllSchemes() {
        return schemeRepository.findAll()
                .stream()
                .map(schemeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SchemeResponseDTO createScheme(SchemeRequestDTO dto) {
        Scheme scheme = schemeMapper.toEntity(dto);
        Scheme saved = schemeRepository.save(scheme);
        return schemeMapper.toDto(saved);
    }

    @Override
    public SchemeResponseDTO getSchemeById(Long id) {
        Scheme scheme = schemeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with ID: " + id));
        return schemeMapper.toDto(scheme);
    }
}
