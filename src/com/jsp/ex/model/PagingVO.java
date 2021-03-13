package com.jsp.ex.model;

public class PagingVO {
	// Member Variable
	private int pageSize;		//게시글 수
	private int totalCount;		//게시글 전체 수
	private int pageNo;			//페이지 번호
	private int firstPageNo;	//첫 번째 페이지 번호
	private int prevPageNo;		//이전 페이지 번호
	private int nextPageNo;		//다음 페이지 번호
	private int finalPageNo;	//마지막 페이지 번호
	private int startPageNo;	//시작 페이지 (페이징 네비 기준)
	private int endPageNo;		//끝 페이지 (페이징 네비 기준)
	
	//Getter & Setter
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		this.makePaging();
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getFirstPageNo() {
		return firstPageNo;
	}
	public void setFirstPageNo(int firstPageNo) {
		this.firstPageNo = firstPageNo;
	}
	public int getPrevPageNo() {
		return prevPageNo;
	}
	public void setPrevPageNo(int prevPageNo) {
		this.prevPageNo = prevPageNo;
	}
	public int getNextPageNo() {
		return nextPageNo;
	}
	public void setNextPageNo(int nextPageNo) {
		this.nextPageNo = nextPageNo;
	}
	public int getFinalPageNo() {
		return finalPageNo;
	}
	public void setFinalPageNo(int finalPageNo) {
		this.finalPageNo = finalPageNo;
	}
	public int getStartPageNo() {
		return startPageNo;
	}
	public void setStartPageNo(int startPageNo) {
		this.startPageNo = startPageNo;
	}
	public int getEndPageNo() {
		return endPageNo;
	}
	public void setEndPageNo(int endPageNo) {
		this.endPageNo = endPageNo;
	}
	
	//페이징 생성
	private void makePaging() {
		//게시글이 없으면
		if (this.totalCount == 0) {
			return;
		}

		//기본 값 설정
		if (this.pageNo == 0) {
			this.setPageNo(1);
		}
		if (this.pageSize == 0) {
			this.setPageSize(10);
		}

		//마지막 페이지
		int finalPage = (totalCount + (pageSize - 1)) / pageSize;
		
		//기본 값 설정
		if (this.pageNo > finalPage) {
			this.setPageNo(finalPage);
		}
		
		//현재 페이지 유효성 체크
		if (this.pageNo < 0 || this.pageNo > finalPage) {
			this.pageNo = 1;
		}
		
		boolean isNowFirst = pageNo == 1 ? true : false;				//시작 페이지 (전체)
		boolean isNowFinal = pageNo == finalPage ? true : false;		//마지막 페이지 (전체)
		
		int startPage = ((pageNo - 1) / 10) * 10 + 1;					//시작 페이지 (페이징 네비 기준)
		int endPage = startPage + 10 - 1;								//끝 페이지 (페이징 네비 기준)

		//[마지막 페이지 (페이징 네비 기준) > 마지막 페이지] 보다 큰 경우
		if (endPage > finalPage) {
			endPage = finalPage;
		}
		
		//첫 번째 페이지 번호
		this.setFirstPageNo(1);

		//이전 페이지 번호
		if (isNowFirst) {
			this.setPrevPageNo(1);
		} else {
			this.setPrevPageNo(((pageNo - 1) < 1 ? 1 : (pageNo - 1)));
		}
		
		this.setStartPageNo(startPage);									//시작 페이지 (페이징 네비 기준)
		this.setEndPageNo(endPage);										//끝 페이지 (페이징 네비 기준)

		//다음 페이지 번호
		if (isNowFinal) {
			this.setNextPageNo(finalPage);								
		} else {
			this.setNextPageNo(((pageNo + 1) > finalPage ? finalPage : (pageNo + 1)));
		}
		
		//마지막 페이지 번호
		this.setFinalPageNo(finalPage);
	}
}