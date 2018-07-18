package rewards.internal.reward;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;

import common.datetime.SimpleDate;

/**
 * JDBC implementation of a reward repository that records the result of a reward transaction by inserting a reward
 * confirmation record.
 */


//	TODO-03: Replace the JDBC code in JdbcRewardRepository with JdbcTemplate.
//  1. Add a field of type JdbcTemplate.  Refactor the constructor to instantiate it.
//	2. Refactor the nextConfirmationNumber() and confirmReward(...) methods to use the template.
//
//     DO NOT delete the old JDBC code, just comment out the try/catch block.
//     You will need to refer to the old JDBC code to write the new JdbcTemplate code.
//
//	4. Save all work, run the JdbcRewardRepositoryTests.  It should pass.

public class JdbcRewardRepository implements RewardRepository {

	private DataSource dataSource;

	public JdbcRewardRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public RewardConfirmation confirmReward(AccountContribution contribution, Dining dining) {
		String sql = "insert into T_REWARD (CONFIRMATION_NUMBER, REWARD_AMOUNT, REWARD_DATE, ACCOUNT_NUMBER, DINING_MERCHANT_NUMBER, DINING_DATE, DINING_AMOUNT) values (?, ?, ?, ?, ?, ?, ?)";
		String confirmationNumber = nextConfirmationNumber();

		// Update the T_REWARD table with the new Reward
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, confirmationNumber);
			ps.setBigDecimal(2, contribution.getAmount().asBigDecimal());
			ps.setDate(3, new Date(SimpleDate.today().inMilliseconds()));
			ps.setString(4, contribution.getAccountNumber());
			ps.setString(5, dining.getMerchantNumber());
			ps.setDate(6, new Date(dining.getDate().inMilliseconds()));
			ps.setBigDecimal(7, dining.getAmount().asBigDecimal());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException("SQL exception occured inserting reward record", e);
		}
		
		return new RewardConfirmation(confirmationNumber, contribution);
	}

	private String nextConfirmationNumber() {
		String sql = "select next value for S_REWARD_CONFIRMATION_NUMBER from DUAL_REWARD_CONFIRMATION_NUMBER";
		String nextValue;
		
		try (Connection conn = dataSource.getConnection(); 
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			rs.next();
			nextValue = rs.getString(1);
		} catch (SQLException e) {
			throw new RuntimeException("SQL exception getting next confirmation number", e);
		}
		
		return nextValue;
	}
}
