document.getElementById('postDetailModal').addEventListener('click', async (event) => {

    if(event.target && event.target.classList.contains('replyInputBtn')){
        let targetComment = event.target.closest('.comment');
        let parentId = targetComment.getAttribute('data-comment-id');
        let commentAuthor = targetComment.querySelector('.commentAuthor').innerText;
        let commentInput = document.getElementById('detailCommentInput');
        commentInput.removeAttribute('data-parent-id');
        commentInput.value = '@'+commentAuthor+' ';
        commentInput.setAttribute('data-parent-id',parentId);
        commentInput.focus();
        commentInput.nextElementSibling.style.display = 'block';
    }

    if(event.target && event.target.classList.contains('replySeeMore')){
        let replyMoreBtn = event.target;
        let replyArea = replyMoreBtn.closest('.reply-area').querySelector('.replyInner');
        let targetComment = replyMoreBtn.closest('.comment');
        let parentId = targetComment.getAttribute('data-comment-id');
        let replyCntSpan =replyMoreBtn.querySelector('.replyCount');
        let replyCnt =  parseInt(replyCntSpan.textContent, 10);
        let page = replyMoreBtn.getAttribute('data-reply-page');
        let replies = await getReplies(parentId,page,replyCnt);

        if(replies.length > 0){
            renderReply(replies,replyArea);
            replyCntSpan.innerText = replyCnt - replies.length;
            replyMoreBtn.setAttribute('data-reply-page',page+1);
        }
        if((replyCnt - replies.length)==0){
            replyMoreBtn.style.display = "none";
            replyMoreBtn.classList.remove('replySeeMore');
            replyMoreBtn.classList.add('replySee');
            replyMoreBtn.parentElement.querySelector('.replyHide').style.display = "block";
        }
        return;
    }

    if(event.target && event.target.classList.contains('replyHide')){
        let replyInner = event.target.closest('.reply-area').querySelector('.replyInner');
        replyInner.style.display = "none";
        let replySeeBtn = event.target.parentElement.querySelector('.replySee');
        replySeeBtn.querySelector('.replyCount').innerText = replyInner.querySelectorAll('.reply').length;
        replySeeBtn.style.display = "block";
        event.target.style.display = "none";
        return;
    }

    if(event.target && event.target.classList.contains('replySee')){
        event.target.style.display = "none";
        let replyInner = event.target.closest('.reply-area').querySelector('.replyInner');
        replyInner.style.display = "block";
        event.target.parentElement.querySelector('.replyHide').style.display = "block";
        return;
    }

});


let isReplyFetching = false; // 중복 요청 방지
let hasMoreReplies = true;
const replySize = 10;
//게시글 페이징 api

async function getReplies(parentId,page,replyCnt){
    if(replyCnt == 0) return;
    if (!hasMoreReplies || isReplyFetching) return;
    isReplyFetching = true;
    const options = {
        noSpinner: true,
        method: 'GET'
    };
    const response = await fetchWithAuth(`/api/comment/${parentId}/replies?page=${page}&size=${replySize}`,options);
    const data = await response.json();
    hasMoreReplies = data.hasNext;
    isReplyFetching = false;
    return data.content;

}

//상세조회 모달 대댓글 등록시 대댓글 렌더링
function renderReply(replies,replyInner){

    let userId = sessionStorage.getItem('userId');

    replies.forEach((reply) => {
        let replyClone = document.getElementById('postReply-template').content.cloneNode(true);
        replyClone.querySelector('.reply').setAttribute('data-reply-id',reply.commentId);
        if(reply.authorProfileImage){
            replyClone.querySelector('.profile-img').src = reply.authorProfileImage;
        }
        replyClone.querySelector('.replyAuthor').innerText = reply.replyAuthor;
        replyClone.querySelector('.replyContent').insertAdjacentHTML('beforeend', reply.replyContent);
        if(reply.memberId != userId){
            replyClone.querySelector('.commentDeleteBtn').remove();
        }
        replyInner.appendChild(replyClone);
    });
}
