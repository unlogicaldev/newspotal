package org.unlogical.dev.demo.news.batch;


import org.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.unlogical.dev.demo.news.common.cache.InfinispanCacheManager;


public class NewsBatchTest extends BaseTest {

	@Autowired NewsBatch newsBatch;
	@Test
	public void testRssBatch() {
		newsBatch.rssBatch();
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetCacheBatch(){
		newsBatch.setCacheBatch();
		fail("Not yet implemented");
	}

	@Test
	public void testSetInfinispanCacheBatch(){
		newsBatch.setInfinispanCacheBatch();
		System.out.println(InfinispanCacheManager.getNewsListCache("all").toString());
		fail("Not yet implemented");
	}
}
