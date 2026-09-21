<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>复利计算与对比工具</title>
    <!-- 引入 Chart.js 用于绘制折线图 -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        :root {
            --primary-color: #2563eb;
            --secondary-color: #dc2626;
            --bg-color: #f8fafc;
            --card-bg: #ffffff;
            --text-color: #1e293b;
            --border-color: #e2e8f0;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
        }

        body {
            background-color: var(--bg-color);
            color: var(--text-color);
            padding: 20px;
        }

        /* 页面整体布局 */
        .page-wrapper {
            display: flex;
            gap: 20px;
            max-width: 1400px;
            margin: 0 auto;
            align-items: flex-start;
        }

        /* 左侧窄竖长条 DIY 广告位 */
        .sidebar-ad {
            width: 220px;
            flex-shrink: 0;
            background: #ffffff;
            border: 1px solid var(--border-color);
            border-radius: 12px;
            padding: 15px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            position: sticky;
            top: 20px;
            text-align: center;
        }

        .ad-header {
            font-size: 0.85rem;
            font-weight: bold;
            color: #d97706;
            background-color: #fef3c7;
            padding: 4px 8px;
            border-radius: 4px;
            display: inline-block;
            margin-bottom: 12px;
            letter-spacing: 0.5px;
        }

        /* 轮播图容器 */
        .carousel-container {
            position: relative;
            width: 100%;
            height: 190px;
            overflow: hidden;
            border-radius: 8px;
            background-color: #f1f5f9;
        }

        .carousel-slide {
            width: 100%;
            height: 100%;
            display: block;
            text-decoration: none;
        }

        .carousel-slide img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.4s ease, opacity 0.4s ease;
        }

        .carousel-slide img:hover {
            transform: scale(1.05);
        }

        /* 商品标题与元信息 */
        .product-info {
            margin-top: 12px;
            min-height: 50px;
        }

        .product-title {
            display: block;
            font-size: 0.95rem;
            font-weight: 600;
            color: #1e293b;
            text-decoration: none;
            line-height: 1.3;
            transition: color 0.2s;
            text-align: left;
            margin-bottom: 4px;
        }

        .product-title:hover {
            color: #d97706;
        }

        .product-price {
            display: block;
            text-align: left;
            font-size: 0.9rem;
            color: #e11d48;
            font-weight: bold;
        }

        /* 轮播圆点指示器 */
        .carousel-dots {
            display: flex;
            justify-content: center;
            gap: 6px;
            margin-top: 10px;
            margin-bottom: 12px;
        }

        .dot {
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background-color: #cbd5e1;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
        }

        .dot.active {
            background-color: #d97706;
            transform: scale(1.2);
        }

        .shop-btn {
            display: block;
            width: 100%;
            padding: 8px 0;
            background-color: #d97706;
            color: #ffffff;
            text-decoration: none;
            font-size: 0.85rem;
            font-weight: bold;
            border-radius: 6px;
            transition: background-color 0.2s;
        }

        .shop-btn:hover {
            background-color: #b45309;
        }

        /* 主体计算器内容区域 */
        .container {
            flex-grow: 1;
            min-width: 0;
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 1.8rem;
        }

        .control-panel {
            background-color: var(--card-bg);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        .toggle-container {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid var(--border-color);
        }

        .toggle-container label {
            cursor: pointer;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .forms-wrapper {
            display: grid;
            grid-template-columns: 1fr;
            gap: 20px;
        }

        @media (min-width: 768px) {
            .forms-wrapper.dual {
                grid-template-columns: 1fr 1fr;
            }
        }

        @media (max-width: 900px) {
            .page-wrapper {
                flex-direction: column;
            }
            .sidebar-ad {
                width: 100%;
                position: relative;
                top: 0;
            }
        }

        .form-card {
            background-color: #f1f5f9;
            padding: 15px;
            border-radius: 6px;
            border-left: 4px solid var(--primary-color);
        }

        .form-card.plan-b {
            border-left-color: var(--secondary-color);
        }

        .form-card h3 {
            margin-bottom: 12px;
            font-size: 1.1rem;
        }

        .form-group {
            margin-bottom: 12px;
        }

        .form-group label {
            display: block;
            margin-bottom: 4px;
            font-size: 0.9rem;
            color: #475569;
        }

        .form-group input {
            width: 100%;
            padding: 8px 12px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
        }

        .hidden {
            display: none !important;
        }

        .chart-container {
            background-color: var(--card-bg);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            position: relative;
            height: 400px;
        }

        .table-container {
            background-color: var(--card-bg);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            text-align: right;
            font-size: 0.95rem;
        }

        th, td {
            padding: 10px 12px;
            border-bottom: 1px solid var(--border-color);
        }

        th {
            background-color: #f8fafc;
            color: #475569;
            font-weight: 600;
        }

        th:first-child, td:first-child {
            text-align: center;
        }
    </style>
</head>
<body>

<div class="page-wrapper">

    <!-- 左侧 DIY 散珠小摆件 动态轮播广告栏 -->
    <aside class="sidebar-ad">
        <span class="ad-header">✨ 手工DIY饰品工坊</span>
        
        <!-- 点击图片可跳转 -->
        <div class="carousel-container">
            <a href="#" id="adImgLink" target="_blank" class="carousel-slide">
                <img id="adImg" src="" alt="DIY商品展示">
            </a>
        </div>

        <!-- 轮播圆点 -->
        <div class="carousel-dots" id="carouselDots"></div>

        <!-- 商品标题与价格（点击标题可跳转） -->
        <div class="product-info">
            <a href="#" id="adTitleLink" target="_blank" class="product-title" id="adTitle">--</a>
            <span class="product-price" id="adPrice">--</span>
        </div>

        <a href="#" id="adShopLink" class="shop-btn" target="_blank">进入店铺选购</a>
    </aside>

    <!-- 主体内容 -->
    <div class="container">
        <h1>复利计算与对比分析</h1>

        <!-- 控制与输入区域 -->
        <div class="control-panel">
            <div class="toggle-container">
                <label>
                    <input type="checkbox" id="enablePlanB" onchange="togglePlanB()">
                    开启第二套方案进行对比
                </label>
            </div>

            <div class="forms-wrapper" id="formsWrapper">
                <!-- 方案 A 输入 -->
                <div class="form-card plan-a">
                    <h3>方案 A 参数</h3>
                    <div class="form-group">
                        <label for="principalA">本金原始数额</label>
                        <input type="number" id="principalA" value="10000" oninput="calculate()">
                    </div>
                    <div class="form-group">
                        <label for="rateA">复利利率 (%)</label>
                        <input type="number" id="rateA" value="0.7" step="0.1" oninput="calculate()">
                    </div>
                    <div class="form-group">
                        <label for="periodA">投资期数</label>
                        <input type="number" id="periodA" value="720" min="1" oninput="calculate()">
                    </div>
                </div>

                <!-- 方案 B 输入 -->
                <div class="form-card plan-b hidden" id="cardB">
                    <h3>方案 B 参数</h3>
                    <div class="form-group">
                        <label for="principalB">本金原始数额</label>
                        <input type="number" id="principalB" value="10000" oninput="calculate()">
                    </div>
                    <div class="form-group">
                        <label for="rateB">复利利率 (%)</label>
                        <input type="number" id="rateB" value="0.1" step="0.1" oninput="calculate()">
                    </div>
                    <div class="form-group">
                        <label for="periodB">投资期数</label>
                        <input type="number" id="periodB" value="720" min="1" oninput="calculate()">
                    </div>
                </div>
            </div>
        </div>

        <!-- 图表展示区域 -->
        <div class="chart-container">
            <canvas id="compoundChart"></canvas>
        </div>

        <!-- 数据表格展示区域 -->
        <div class="table-container">
            <table id="resultTable">
                <thead>
                    <tr id="tableHeader">
                        <th>期数</th>
                        <th>方案A 本息总额</th>
                        <th>方案A 累计利息</th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <!-- 数据由 JS 动态渲染 -->
                </tbody>
            </table>
        </div>
    </div>

</div>

<script>

    // 手工 DIY 散珠及摆件商品数据配置
    let diyProducts = [
    ];

    function getHotSaleList(){ 
        fetch('/taobao/productSource/getHotSaleList', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            },
            // body: JSON.stringify({ /* 如果需要传参可以在这里写 */ })
        })
        .then(response => {
            // 根据后端返回的数据类型选择：res.text() 或 res.json()
            return response.json(); 
        })
        .then(data => {
            if (data && data.success && data.productSourceList) {
                diyProducts.length = 0;
                // 映射接口字段到 diyProducts 结构
                diyProducts = data.productSourceList.map(item => ({
                    title: item.commodityName, // 对应 commodityName
                    img: "https://gw.alicdn.com/imgextra/" + item.commodityImgName + "_500x500xz_.webp", // 拼接图片地址[cite: 1]
                    url: "https://item.taobao.com/item.html?id=" + item.commodityId // 拼接商品链接（使用 commodityId）[cite: 1]
                    <%-- price: item.includePostage ? "包邮" : "热卖推荐", // 依据 includePostage 动态显示 --%>
                }));

                // 数据加载成功后，重新初始化轮播图和圆点
                if (diyProducts.length > 0) {
                    initDots();
                    showDiyProduct(0);
                }
            }
        })
        .catch(error => {
            console.error('获取热卖商品列表失败:', error);
        });
    }

    getHotSaleList();



    let currentDiyIndex = 0;

    // 初始化指示点
    function initDots() {
        const dotsContainer = document.getElementById('carouselDots');
        dotsContainer.innerHTML = '';
        diyProducts.forEach(function(_, idx) {
            const dot = document.createElement('div');
            dot.className = 'dot ' + (idx === 0 ? 'active' : '');
            dot.onclick = function() { showDiyProduct(idx); };
            dotsContainer.appendChild(dot);
        });
    }

    // 渲染指定商品
    function showDiyProduct(index) {
        // 增加防护：如果数组为空，或者索引超出范围，直接退出，防止报错
        if (!diyProducts || diyProducts.length === 0 || index >= diyProducts.length) {
            return;
        }
        
        currentDiyIndex = index;
        const item = diyProducts[index];
        
        // 再次加固：确保 item 存在
        if (!item) return;
    
        const imgEl = document.getElementById('adImg');
        const titleEl = document.getElementById('adTitleLink');
        const priceEl = document.getElementById('adPrice');
        const imgLink = document.getElementById('adImgLink');
        const shopLink = document.getElementById('adShopLink');
    
        // 设置链接与数据
        imgEl.src = item.img;
        titleEl.textContent = item.title;
        titleEl.href = item.url;
        priceEl.textContent = item.price;
        imgLink.href = item.url;
        shopLink.href = item.url;
    
        // 更新点高亮
        const dots = document.querySelectorAll('.carousel-dots .dot');
        dots.forEach(function(dot, idx) {
            dot.classList.toggle('active', idx === index);
        });
    }

    // 定时轮播 (每3.5秒)
    function autoPlayCarousel() {
        currentDiyIndex = (currentDiyIndex + 1) % diyProducts.length;
        showDiyProduct(currentDiyIndex);
    }

    initDots();
    showDiyProduct(0);
    setInterval(autoPlayCarousel, 3500);

    let chartInstance = null;

    function formatCurrency(num) {
        return new Intl.NumberFormat('zh-CN', { style: 'currency', currency: 'CNY' }).format(num);
    }

    function togglePlanB() {
        const isDual = document.getElementById('enablePlanB').checked;
        const cardB = document.getElementById('cardB');
        const formsWrapper = document.getElementById('formsWrapper');

        if (isDual) {
            cardB.classList.remove('hidden');
            formsWrapper.classList.add('dual');
        } else {
            cardB.classList.add('hidden');
            formsWrapper.classList.remove('dual');
        }
        calculate();
    }

    function calculate() {
        const isDual = document.getElementById('enablePlanB').checked;

        const pA = parseFloat(document.getElementById('principalA').value) || 0;
        const rA = (parseFloat(document.getElementById('rateA').value) || 0) / 100;
        const nA = parseInt(document.getElementById('periodA').value) || 0;

        const pB = parseFloat(document.getElementById('principalB').value) || 0;
        const rB = (parseFloat(document.getElementById('rateB').value) || 0) / 100;
        const nB = parseInt(document.getElementById('periodB').value) || 0;

        const maxYears = isDual ? Math.max(nA, nB) : nA;

        const labels = [];
        const dataA = [];
        const dataB = [];
        const tableRows = [];

        for (let year = 0; year <= maxYears; year++) {
            labels.push('第 ' + year + ' 期');

            let valA = year <= nA ? pA * Math.pow(1 + rA, year) : null;
            if (valA !== null) dataA.push(valA.toFixed(2));

            let valB = isDual && year <= nB ? pB * Math.pow(1 + rB, year) : null;
            if (isDual && valB !== null) dataB.push(valB.toFixed(2));

            tableRows.push({
                year: year,
                valA: valA,
                interestA: valA !== null ? valA - pA : null,
                valB: valB,
                interestB: valB !== null ? valB - pB : null,
                diff: (valA !== null && valB !== null) ? valA - valB : null
            });
        }

        renderChart(labels, dataA, dataB, isDual);
        renderTable(tableRows, isDual);
    }

    function renderChart(labels, dataA, dataB, isDual) {
        const ctx = document.getElementById('compoundChart').getContext('2d');

        const datasets = [{
            label: '方案 A 本息总额',
            data: dataA,
            borderColor: '#2563eb',
            backgroundColor: 'rgba(37, 99, 235, 0.1)',
            fill: true,
            tension: 0.2
        }];

        if (isDual) {
            datasets.push({
                label: '方案 B 本息总额',
                data: dataB,
                borderColor: '#dc2626',
                backgroundColor: 'rgba(220, 38, 38, 0.1)',
                fill: true,
                tension: 0.2
            });
        }

        if (chartInstance) {
            chartInstance.destroy();
        }

        chartInstance = new Chart(ctx, {
            type: 'line',
            data: { labels: labels, datasets: datasets },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                interaction: { mode: 'index', intersect: false },
                plugins: {
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return context.dataset.label + ': ' + formatCurrency(context.raw);
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        ticks: {
                            callback: function(value) {
                                return '¥' + value.toLocaleString();
                            }
                        }
                    }
                }
            }
        });
    }

    function renderTable(rows, isDual) {
        const header = document.getElementById('tableHeader');
        const body = document.getElementById('tableBody');

        if (isDual) {
            header.innerHTML = '<th>期数</th>' +
                '<th>方案A 本息总额</th>' +
                '<th>方案A 累计利息</th>' +
                '<th>方案B 本息总额</th>' +
                '<th>方案B 累计利息</th>' +
                '<th>差额 (A - B)</th>';
        } else {
            header.innerHTML = '<th>期数</th>' +
                '<th>方案A 本息总额</th>' +
                '<th>方案A 累计利息</th>';
        }

        body.innerHTML = rows.map(function(row) {
            if (isDual) {
                return '<tr>' +
                    '<td>' + row.year + '</td>' +
                    '<td>' + (row.valA !== null ? formatCurrency(row.valA) : '-') + '</td>' +
                    '<td>' + (row.interestA !== null ? formatCurrency(row.interestA) : '-') + '</td>' +
                    '<td>' + (row.valB !== null ? formatCurrency(row.valB) : '-') + '</td>' +
                    '<td>' + (row.interestB !== null ? formatCurrency(row.interestB) : '-') + '</td>' +
                    '<td style="color: ' + (row.diff >= 0 ? '#dc2626' : '#16a34a') + '">' +
                        (row.diff !== null ? formatCurrency(row.diff) : '-') +
                    '</td>' +
                '</tr>';
            } else {
                return '<tr>' +
                    '<td>' + row.year + '</td>' +
                    '<td>' + formatCurrency(row.valA) + '</td>' +
                    '<td>' + formatCurrency(row.interestA) + '</td>' +
                '</tr>';
            }
        }).join('');
    }

    window.onload = calculate;
</script>

</body>
</html>