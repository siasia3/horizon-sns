function toggleNotification() {
    const dropdown = document.getElementById('notification-dropdown');
    const isVisible = dropdown.style.display !== 'none';
    dropdown.style.display = isVisible ? 'none' : 'flex';
    if (!isVisible) {
        loadNotifications();
    }
}

// 드롭다운 외부 클릭 시 닫기
document.addEventListener('click', function (e) {
    const wrapper = document.getElementById('notification-wrapper');
    if (wrapper && !wrapper.contains(e.target)) {
        document.getElementById('notification-dropdown').style.display = 'none';
    }
});

async function loadNotifications() {
    try {
        const res = await fetchWithAuth('/api/notifications', {noSpinner: true});
        const data = await res.json();
        renderNotifications(data);
    } catch (err) {
        console.error('알림 로드 실패:', err);
    }
}

function renderNotifications(notifications) {
    const list = document.getElementById('notification-list');
    const template = document.getElementById('notification-item-template');
    list.innerHTML = '';

    if (!notifications || notifications.length === 0) {
        list.innerHTML = '<div class="notification-empty">알림이 없습니다</div>';
        updateBadge(0);
        return;
    }

    let unreadCount = 0;
    notifications.forEach(noti => {
        const clone = template.content.cloneNode(true);
        const item = clone.querySelector('.notification-item');
        const img = clone.querySelector('.notification-profile-img');
        const message = clone.querySelector('.notification-message');
        const time = clone.querySelector('.notification-time');

        if (!noti.read) {
            unreadCount++;
        } else {
            item.classList.remove('unread');
        }

        img.src = noti.senderProfileImage || '/image/basicProfile.jpg';
        img.alt = noti.senderNickname || '';
        message.textContent = noti.message || '';
        time.textContent = formatTime(noti.createdAt);

        item.dataset.id = noti.id;
        item.addEventListener('click', () => onNotificationClick(noti));
        list.appendChild(clone);
    });

    updateBadge(unreadCount);
}

async function onNotificationClick(noti) {
    if (!noti.read) {
        try {
            await fetchWithAuth(`/api/notifications/${noti.id}/read`, { noSpinner: true, method: 'PATCH' });
        } catch (err) {
            console.error('읽음 처리 실패:', err);
        }

        const item = document.querySelector(`.notification-item[data-id="${noti.id}"]`);
        if (item) {
            item.classList.remove('unread');
            const dot = item.querySelector('.notification-dot');
            if (dot) dot.style.visibility = 'hidden';
        }

        const badge = document.getElementById('notification-badge');
        const current = parseInt(badge.textContent) || 0;
        updateBadge(Math.max(0, current - 1));
    }
}

async function markAllRead() {
    try {
        await fetchWithAuth('/api/notifications/read-all', { noSpinner: true, method: 'PATCH' });
        document.querySelectorAll('.notification-item.unread').forEach(item => {
            item.classList.remove('unread');
            const dot = item.querySelector('.notification-dot');
            if (dot) dot.style.visibility = 'hidden';
        });
        updateBadge(0);
    } catch (err) {
        console.error('모두 읽음 실패:', err);
    }
}

function updateBadge(count) {
    const badge = document.getElementById('notification-badge');
    if (count > 0) {
        badge.textContent = count > 99 ? '99+' : count;
        badge.style.display = 'flex';
    } else {
        badge.style.display = 'none';
    }
}

function formatTime(dateStr) {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const now = new Date();
    const diff = Math.floor((now - date) / 1000);
    if (diff < 60) return '방금 전';
    if (diff < 3600) return `${Math.floor(diff / 60)}분 전`;
    if (diff < 86400) return `${Math.floor(diff / 3600)}시간 전`;
    return `${Math.floor(diff / 86400)}일 전`;
}

// SSE 연결
function connectSSE() {
    const eventSource = new EventSource('/api/notifications/stream');

    eventSource.onmessage = function (e) {
        const noti = JSON.parse(e.data);
        const badge = document.getElementById('notification-badge');
        const current = parseInt(badge.textContent) || 0;
        updateBadge(current + 1);

        // 드롭다운이 열려있으면 새 알림 즉시 추가
        const dropdown = document.getElementById('notification-dropdown');
        if (dropdown.style.display !== 'none') {
            prependNotification(noti);
        }
    };

    eventSource.onerror = function () {
        eventSource.close();
        // 5초 후 재연결
        setTimeout(connectSSE, 5000);
    };
}

function prependNotification(noti) {
    const list = document.getElementById('notification-list');
    const empty = list.querySelector('.notification-empty');
    if (empty) empty.remove();

    const template = document.getElementById('notification-item-template');
    const clone = template.content.cloneNode(true);
    const item = clone.querySelector('.notification-item');
    const img = clone.querySelector('.notification-profile-img');
    const message = clone.querySelector('.notification-message');
    const time = clone.querySelector('.notification-time');

    img.src = noti.senderProfileImage || '/image/basicProfile.jpg';
    img.alt = noti.senderNickname || '';
    message.textContent = noti.message || '';
    time.textContent = formatTime(noti.createdAt);
    item.dataset.id = noti.id;
    item.addEventListener('click', () => onNotificationClick(noti));

    list.prepend(clone);
}

// 페이지 로드 시 미읽음 배지 초기화 및 SSE 연결
document.addEventListener('DOMContentLoaded', async function () {
    try {
        const res = await fetchWithAuth('/api/notifications/unread-count', {noSpinner: true});
        const data = await res.json();
        updateBadge(data.count ?? data);
    } catch {}

    connectSSE();
});