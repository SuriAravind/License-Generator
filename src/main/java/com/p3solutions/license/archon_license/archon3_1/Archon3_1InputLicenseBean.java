package com.p3solutions.license.archon_license.archon3_1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Suriyanarayanan K
 * on 08/02/21 12:55 PM.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Archon3_1InputLicenseBean {
    private String keyPath;
    private String issueDate;
    private String releaseDate;
    private String validDate;
    private String licensedTo;
    private List<String> dsCategory;
    private List<String> rdbmsCategory;
}
