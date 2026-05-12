//package com.cts.scheme.service;
//
//import com.cts.scheme.model.Scheme;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//@Service
//public interface SchemeService {
//
//    public Scheme createScheme(Scheme scheme);
//    public List<Scheme> getAllSchemes();
//    public Scheme getSchemeById(Long id);
//}


package com.cts.scheme.service;

import com.cts.scheme.dto.SchemeRequestDTO;
import com.cts.scheme.dto.SchemeResponseDTO;
import java.util.List;

public interface SchemeService {
    List<SchemeResponseDTO> getAllSchemes();
    SchemeResponseDTO getSchemeById(Long id);
    SchemeResponseDTO createScheme(SchemeRequestDTO dto);
}