package com.p3solutions.license.archon_license.archon3_1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Suriyanarayanan K
 * on 09/02/21 2:45 PM.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Archon3_1LicenseBean {
    private String issueDate;
    private String releaseDate;
    private String validDate;
    private String licensedTo;
    private List<Archon3_1CategoryLicenseBean> dsCategory;
    private List<Archon3_1CategoryLicenseBean> rdbmsCategory;
}
