package bcJsonRpc.pojo;

import bcJsonRpc.BitcoindUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.math.BigDecimal;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown=true)
public class ListUnspentTO {
	 String txid;
	 int vout;
	 String address;
	 String account;
	 String scriptPubKey;
	 BigDecimal amount;
	 int confirmations;
	 boolean spendable;
	 boolean solvable;
	 boolean safe;
	 String redeemScript;

	public ListUnspentTO() {
	}

	public String getTxid() {
		return txid;
	}
	public void setTxid(String txid) {
		this.txid = txid;
	}
	public int getVout() {
		return vout;
	}
	public void setVout(int vout) {
		this.vout = vout;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getScriptPubKey() {
		return scriptPubKey;
	}
	public void setScriptPubKey(String scriptPubKey) {
		this.scriptPubKey = scriptPubKey;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public int getConfirmations() {
		return confirmations;
	}
	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}
	public boolean isSpendable() {
		return spendable;
	}
	public void setSpendable(boolean spendable) {
		this.spendable = spendable;
	}
	public boolean isSolvable() {
		return solvable;
	}
	public void setSolvable(boolean solvable) {
		this.solvable = solvable;
	}
	public boolean isSafe() {
		return safe;
	}
	public void setSafe(boolean safe) {
		this.safe = safe;
	}

	public String getRedeemScript() {
		return redeemScript;
	}

	public void setRedeemScript(String redeemScript) {
		this.redeemScript = redeemScript;
	}

	@Override
	public String toString() {
		return "ListUnspentTO [txid=" + txid + ", vout=" + vout + ", address=" + address + ", account=" + account
				+ ", scriptPubKey=" + scriptPubKey + ", amount=" + amount + ", confirmations=" + confirmations
				+ ", spendable=" + spendable + ", solvable=" + solvable + ", safe=" + safe + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ListUnspentTO that = (ListUnspentTO) o;
		return vout == that.vout &&
				spendable == that.spendable &&
				Objects.equals(txid, that.txid) &&
				Objects.equals(amount, that.amount);
	}

	@Override
	public int hashCode() {

		return Objects.hash(txid, vout, address, account, scriptPubKey, amount, confirmations, spendable, solvable, safe, redeemScript);
	}
}
