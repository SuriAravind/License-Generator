package com.p3solutions.license.archon_license.archon3_1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Suriyanarayanan K
 * on 09/02/21 2:46 PM.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Archon3_1CategoryLicenseBean {
    private String name;
    private Date validateDate;
}
