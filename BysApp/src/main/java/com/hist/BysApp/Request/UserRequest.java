package com.hist.BysApp.Request;

import java.util.List;

import com.hist.BysApp.model.NUserDTO;
import com.hist.BysApp.model.ResultDTO;
import lombok.Data;
@Data
public class UserRequest {
   List<NUserDTO> results;
}
