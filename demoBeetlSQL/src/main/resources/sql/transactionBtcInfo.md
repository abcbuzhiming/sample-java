sample
===
* 注释

	select #use("cols")# from transaction_btc_info where #use("condition")#

cols
===

	id,time_stamp,price,amount,tid,transaction_type

updateSample
===

	`id`=#id#,`time_stamp`=#timeStamp#,`price`=#price#,`amount`=#amount#,`tid`=#tid#,`transaction_type`=#transactionType#

condition
===

	1 = 1  
	@if(!isEmpty(timeStamp)){
	 and `time_stamp`=#timeStamp#
	@}
	@if(!isEmpty(price)){
	 and `price`=#price#
	@}
	@if(!isEmpty(amount)){
	 and `amount`=#amount#
	@}
	@if(!isEmpty(tid)){
	 and `tid`=#tid#
	@}
	@if(!isEmpty(transactionType)){
	 and `transaction_type`=#transactionType#
	@}
	
