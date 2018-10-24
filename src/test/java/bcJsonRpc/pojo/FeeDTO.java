package bcJsonRpc.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.math.BigDecimal;

@JsonInclude(Include.NON_NULL)
public class FeeDTO {
	private BigDecimal feerate;
	private Integer blocks;

	public FeeDTO(BigDecimal feerate, Integer blocks) {
		super();
		this.feerate = feerate;
		this.blocks = blocks;
	}

	public FeeDTO() {
	}

	public Integer getBlocks() {
		return blocks;
	}

	public void setBlocks(Integer blocks) {
		this.blocks = blocks;
	}

	public BigDecimal getFeerate() {
		return feerate;
	}

	public void setFeerate(BigDecimal feerate) {
		this.feerate = feerate;
	}

	@Override
	public String toString() {
		return "FeeDTO [feerate=" + feerate + ", blocks=" + blocks + "]";
	}

}
