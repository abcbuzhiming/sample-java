sample
===
* 注释

	select #use("cols")# from verification_code where #use("condition")#

cols
===

	id,mobile_phone,code,ip,create_time

updateSample
===

	`id`=#id#,`mobile_phone`=#mobilePhone#,`code`=#code#,`ip`=#ip#,`create_time`=#createTime#

condition
===

	1 = 1  
	@if(!isEmpty(mobilePhone)){
	 and `mobile_phone`=#mobilePhone#
	@}
	@if(!isEmpty(code)){
	 and `code`=#code#
	@}
	@if(!isEmpty(ip)){
	 and `ip`=#ip#
	@}
	@if(!isEmpty(createTime)){
	 and `create_time`=#createTime#
	@}
	
