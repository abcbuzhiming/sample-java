sample
===
* 注释

	select #use("cols")# from test1 where #use("condition")#

cols
===

	id,value_int,value_varchar

updateSample
===

	`id`=#id#,`value_int`=#valueInt#,`value_varchar`=#valueVarchar#

condition
===

	1 = 1  
	@if(!isEmpty(valueInt)){
	 and `value_int`=#valueInt#
	@}
	@if(!isEmpty(valueVarchar)){
	 and `value_varchar`=#valueVarchar#
	@}
	
getCount
===
  	select count(1) from `test1`
