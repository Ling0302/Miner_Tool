package vo;

import java.util.List;

public class S9MinerVO {

	private S9SummaryVO summary;
	private List<S9PoolVO> pools;
	private S9AntMinerVO antMiner;
	private S9SystemInfoVO systemInfo;
	public S9SummaryVO getSummary() {
		return summary;
	}
	public void setSummary(S9SummaryVO summary) {
		this.summary = summary;
	}
	public List<S9PoolVO> getPools() {
		return pools;
	}
	public void setPools(List<S9PoolVO> pools) {
		this.pools = pools;
	}
	public S9AntMinerVO getAntMiner() {
		return antMiner;
	}
	public void setAntMiner(S9AntMinerVO antMiner) {
		this.antMiner = antMiner;
	}
	public S9SystemInfoVO getSystemInfo() {
		return systemInfo;
	}
	public void setSystemInfo(S9SystemInfoVO systemInfo) {
		this.systemInfo = systemInfo;
	}
	
	
}
