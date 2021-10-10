package vo;

import java.util.List;

public class WhatsMinerVO {

	private List<WhatsMinerPoolVO> poolList;
	private WhatsMinerSummaryVO summaryVO;
	private WhatsMinerSystemVO systemVO;
	private WhatsMinerDevicesVO devicesVO;
	
	public List<WhatsMinerPoolVO> getPoolList() {
		return poolList;
	}
	public void setPoolList(List<WhatsMinerPoolVO> poolList) {
		this.poolList = poolList;
	}
	public WhatsMinerSummaryVO getSummaryVO() {
		return summaryVO;
	}
	public void setSummaryVO(WhatsMinerSummaryVO summaryVO) {
		this.summaryVO = summaryVO;
	}
	public WhatsMinerSystemVO getSystemVO() {
		return systemVO;
	}
	public void setSystemVO(WhatsMinerSystemVO systemVO) {
		this.systemVO = systemVO;
	}
	public WhatsMinerDevicesVO getDevicesVO() {
		return devicesVO;
	}
	public void setDevicesVO(WhatsMinerDevicesVO devicesVO) {
		this.devicesVO = devicesVO;
	}
	
}
