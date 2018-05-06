package net.rohr.sp;

public class ScheduleParser {

	public static void main(String[] argv){
		if(argv.length == 0){
			System.err.println("ScheduleParser: No Schedule attached");
			System.exit(0);
		}
		parse(argv[0]);
	}

	private static void parse(String schedule){
		String operand;
		int lockType;
		String transactionName;
		String accessedVariable;
		while(schedule.length() > 0){
			operand = readOperand(schedule);
			if(operand == null){
				System.err.println("Cannot read Operand");
				System.exit(-1);
			}
			schedule = schedule.substring(operand.length());

			lockType = readLockType(operand);
			if(lockType == -1){
				System.err.println("Operand is null");
			} else if(lockType == -2){
				System.err.println("Unknown Operand: " + operand);
			} else {
				operand = operand.substring(1);
				transactionName = readTransactionName(operand);
				if (transactionName == null){
					System.err.println("Operand has no Transactionname");
				} else {
					operand = operand.substring(transactionName.length());
					accessedVariable = readAccessedVariable(operand);
					if (accessedVariable == null){
						System.err.println("Operand has no AccessedVariable");
					} else {
						System.out.println(lockType);
						System.out.println(transactionName);
						System.out.println(accessedVariable);
					}
				}
			}
		}
	}

	private static String readOperand(String schedule){
		int offset = schedule.indexOf(")");
		if(offset == -1){
			return null;
		}else{
			String result = schedule.substring(0,offset+1);
			return result;
		}
	}

	private static int readLockType(String operand){
		int returnCode;
		if (operand == null) {
			returnCode = -1;
		} else {
			String lockType = operand.substring(0,1);
			switch(lockType){
				case "r":
					returnCode = 0;
					break;
				case "w":
					returnCode = 1;
					break;
				default:
					returnCode = -2;
			}
		}
		return returnCode;
	}

	private static String readTransactionName(String operand){
		String transactionName;
		if (operand == null) {
			transactionName = null;
		} else {
			int offset = operand.indexOf("(");
			if (offset == -1 || offset == 0) {
				transactionName = null;
			} else {
				transactionName = operand.substring(0, offset);
				operand = operand.substring(offset+1);
			}
		}
		return transactionName;
	}

	private static String readAccessedVariable(String operand){
		String accessedVariable;
		if (operand == null) {
			accessedVariable = null;
		} else {
			operand = operand.substring(1);
			int offset = operand.indexOf(")");
			if(offset == -1 || offset == 0) {
				accessedVariable = null;
			} else {
				accessedVariable = operand.substring(0,offset);
			}
		}
		return accessedVariable;
	}
}
