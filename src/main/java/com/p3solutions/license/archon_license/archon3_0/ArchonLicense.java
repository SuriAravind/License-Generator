package com.p3solutions.license.archon_license.archon3_0;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchonLicense {
	private String issueDate;
	private String releaseDate;
	private String validDate;
	private String licensedTo;
	private Map<String, List<String>> dbValue;
	private Map<String,List<String>> datasourceValue;
	
}
