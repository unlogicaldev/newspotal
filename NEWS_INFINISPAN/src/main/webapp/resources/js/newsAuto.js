var page = 0;

function setupPageDefault(){
	$( "#tabs" ).tabs({
		create: function(event, ui) { loadNewsList(); }
		,show: function(event, ui) { loadNewsList(); }
	});
	
	setTimeout(function(){startNewsList();},1000);
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
	var t = '<li><dl><dt><a style="color:blue;" href="'+s.link+'">'+s.title+'</a></dt><dd>'+s.description+'<span class="writing">'+s.creator+'</span><span class="date">'+s.date+'</span></dd></dl></li>';
	return t;
}

function startNewsList(){
	 var result = Math.floor(Math.random() * $(".ui-state-default").length);
	 var id = $(".ui-state-active a").attr("href");
	  $(id).addClass("ui-tabs-hide");
	  id = $(".ui-state-default:eq("+result+") a").attr("href");
	  $(id).removeClass("ui-tabs-hide");
	  $(".ui-state-active").removeClass("ui-tabs-selected");
	  $(".ui-state-active").removeClass("ui-state-active");
	  $(".ui-state-default:eq("+result+")").addClass("ui-tabs-selected");
	  $(".ui-state-default:eq("+result+")").addClass("ui-state-active");
	  loadNewsListInt($(".ui-state-active a").html());
}


function loadNewsListInt(category){
	if(category == "최신뉴스") category = "all";
	$.ajax({
		type : "post",
		url : "/news/list/"+encodeURIComponent(category),
		data : { page : page },
		success : function(data){
			loadNewsListResult(data);	
			setTimeout(function(){startNewsList();},50);
		},
		dataType : "json"
	});
}