# Trade Reporting

This a simple daily trade reporting project for instructions sent by various clients to a bank to execute in the international
market. It takes input as a instructions and prints the daily report in console.  

#Sample format for Instruction.

It will have the below fields.
1)Entity 
2)Trade type:Buy/Sell 
3)AgreedFx Currency 
4)InstructionDate 
5)SettlementDate 
6)Units 
7)Price per unit

#WorkingDaysServiceImpl
Based on the currency type of each instruction the settlement date may be changed.A work week starts Monday and ends Friday, unless the currency of the trade is AED or SAR.
WorkingDaysServiceImpl class will be used to calculate the Arabia settlement Working Day and other currency Working Days

#nstructionReportServiceImpl
The InstructionReportServiceImpl class uses to create a report that shows  
 Amount in USD settled incoming everyday 
 Amount in USD settled outgoing everyday 
 Ranking of entities based on incoming and outgoing amount.

 