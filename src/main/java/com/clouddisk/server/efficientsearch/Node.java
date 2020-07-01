package com.clouddisk.server.efficientsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Node {
    private String e;
    private Set<String> Cind;
}
