sample
===
* 注释

	select #use("cols")# from teacher_info where #use("condition")#

cols
===

	id,user_name,password,mobile_phone,qq_open_id,weixin_open_id,nick_name,true_name,introduce_self,education,graduation_date,working_school,professional_field,accept_consultation_status,teach_level,accumulate_points

updateSample
===

	`id`=#id#,`user_name`=#userName#,`password`=#password#,`mobile_phone`=#mobilePhone#,`qq_open_id`=#qqOpenId#,`weixin_open_id`=#weixinOpenId#,`nick_name`=#nickName#,`true_name`=#trueName#,`introduce_self`=#introduceSelf#,`education`=#education#,`graduation_date`=#graduationDate#,`working_school`=#workingSchool#,`professional_field`=#professionalField#,`accept_consultation_status`=#acceptConsultationStatus#,`teach_level`=#teachLevel#,`accumulate_points`=#accumulatePoints#

condition
===

	1 = 1  
	@if(!isEmpty(userName)){
	 and `user_name`=#userName#
	@}
	@if(!isEmpty(password)){
	 and `password`=#password#
	@}
	@if(!isEmpty(mobilePhone)){
	 and `mobile_phone`=#mobilePhone#
	@}
	@if(!isEmpty(qqOpenId)){
	 and `qq_open_id`=#qqOpenId#
	@}
	@if(!isEmpty(weixinOpenId)){
	 and `weixin_open_id`=#weixinOpenId#
	@}
	@if(!isEmpty(nickName)){
	 and `nick_name`=#nickName#
	@}
	@if(!isEmpty(trueName)){
	 and `true_name`=#trueName#
	@}
	@if(!isEmpty(introduceSelf)){
	 and `introduce_self`=#introduceSelf#
	@}
	@if(!isEmpty(education)){
	 and `education`=#education#
	@}
	@if(!isEmpty(graduationDate)){
	 and `graduation_date`=#graduationDate#
	@}
	@if(!isEmpty(workingSchool)){
	 and `working_school`=#workingSchool#
	@}
	@if(!isEmpty(professionalField)){
	 and `professional_field`=#professionalField#
	@}
	@if(!isEmpty(acceptConsultationStatus)){
	 and `accept_consultation_status`=#acceptConsultationStatus#
	@}
	@if(!isEmpty(teachLevel)){
	 and `teach_level`=#teachLevel#
	@}
	@if(!isEmpty(accumulatePoints)){
	 and `accumulate_points`=#accumulatePoints#
	@}
	
