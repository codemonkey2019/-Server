package com.clouddisk.server.communication.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownLoadAnswer implements Serializable {
    private List<String> fileNames;
    private List<Long> filesLength;
}
