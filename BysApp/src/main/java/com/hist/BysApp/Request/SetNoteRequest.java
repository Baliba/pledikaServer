package com.hist.BysApp.Request;

import java.util.List;

import com.hist.BysApp.model.ResultDTO;

import lombok.Data;
@Data
public class SetNoteRequest {
   List<ResultDTO> results;
}
