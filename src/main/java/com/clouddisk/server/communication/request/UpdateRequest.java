package com.clouddisk.server.communication.request;

import com.clouddisk.server.efficientsearch.KFNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest implements Serializable {
    private List<KFNode> kfNodes;
    private String fileName;
}
