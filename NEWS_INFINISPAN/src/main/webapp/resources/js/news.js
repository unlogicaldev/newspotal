var page = 0;

function setupPageDefault(){
	$( "#tabs" ).tabs({
		create: function(event, ui) { loadNewsList(); }
		,show: function(event, ui) { loadNewsList(); }
	});
	
	//setTimeout(function(){startNewsList();},1000);
}

function loadNewsList(){
	var category = $(".ui-state-active a").html();
	if(category == "최신뉴스") category = "all";
	$.ajax({
		type : "post",
		url : "/news/list/"+encodeURIComponent(category),
		data : { page : page },
		success : function(data){
			loadNewsListResult(data);				
		},
		dataType : "json"
	});
}

function loadNewsListResult(data){
	if(data.list){
		$("#"+$(".ui-state-active a").html()).html("<ul></ul>");
		$.each(data.list,function(i,s){
			$("#"+$(".ui-state-active a").html()+" ul").append(makeNewsHtml(s));
		});
	}
}

function makeNewsHtml(s){
	if(s.description.indexOf('www.youtube.com') > -1) s.description = "테스트기사.!!!";
	var t = '<li><dl><dt><a style="color:blue;" href="'+s.link+'">'+s.title+'</a></dt><dd>'+s.description+'<span class="writing">'+s.creator+'</span><span class="date">'+s.date+'</span></dd></dl></li>';
	return t;
}