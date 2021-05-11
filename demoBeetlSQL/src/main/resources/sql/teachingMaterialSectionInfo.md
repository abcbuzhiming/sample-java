sample
===
* 注释

	select #use("cols")# from teaching_material_section_info where #use("condition")#

cols
===

	id,teaching_material_chapter_id,section_num,section_name,gensee_courseware_id,gensee_courseware_info

updateSample
===

	`id`=#id#,`teaching_material_chapter_id`=#teachingMaterialChapterId#,`section_num`=#sectionNum#,`section_name`=#sectionName#,`gensee_courseware_id`=#genseeCoursewareId#,`gensee_courseware_info`=#genseeCoursewareInfo#

condition
===

	1 = 1  
	@if(!isEmpty(teachingMaterialChapterId)){
	 and `teaching_material_chapter_id`=#teachingMaterialChapterId#
	@}
	@if(!isEmpty(sectionNum)){
	 and `section_num`=#sectionNum#
	@}
	@if(!isEmpty(sectionName)){
	 and `section_name`=#sectionName#
	@}
	@if(!isEmpty(genseeCoursewareId)){
	 and `gensee_courseware_id`=#genseeCoursewareId#
	@}
	@if(!isEmpty(genseeCoursewareInfo)){
	 and `gensee_courseware_info`=#genseeCoursewareInfo#
	@}
	
