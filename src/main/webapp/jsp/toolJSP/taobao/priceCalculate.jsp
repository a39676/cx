<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>价格与运费批量计算器</title>
    <style>
        :root {
            --bg-color: #0f172a;
            --card-bg: #1e293b;
            --text-main: #f8fafc;
            --text-muted: #94a3b8;
            --border-color: #334155;
            --input-bg: #1e293b;
            --focus-border: #3b82f6;
            --table-hover: #1e293b;
            --table-border: #334155;
            --table-stripe: #1e293b;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background-color: var(--bg-color);
            color: var(--text-main);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            transition: background-color 0.3s, color 0.3s;
            padding: 20px 0;
        }

        .calculator-card {
            background-color: var(--card-bg);
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.3), 0 8px 10px -6px rgba(0, 0, 0, 0.3);
            width: 100%;
            max-width: 950px;
        }

        .input-row {
            display: flex;
            gap: 15px;
            margin-bottom: 25px;
            flex-wrap: wrap;
        }

        .input-group {
            flex: 1;
            min-width: 120px;
            display: flex;
            flex-direction: column;
        }

        .input-group label {
            font-size: 13px;
            font-weight: 600;
            color: var(--text-muted);
            margin-bottom: 8px;
            white-space: nowrap;
        }

        .input-wrapper {
            position: relative;
            display: flex;
            align-items: center;
        }

        .prefix-symbol, .suffix-symbol {
            position: absolute;
            color: #64748b;
            font-weight: bold;
            font-size: 14px;
        }

        .prefix-symbol { left: 12px; }
        .suffix-symbol { right: 12px; }

        input[type="number"] {
            width: 100%;
            border: 2px solid var(--border-color);
            background-color: var(--input-bg);
            border-radius: 8px;
            font-size: 15px;
            font-weight: 600;
            color: var(--text-main);
            outline: none;
            transition: all 0.2s;
            box-sizing: border-box;
            padding: 10px;
        }

        .price-input, .shipping-input, .shipping-inc-input, .discount-input, .free-shipping-input { padding-left: 30px !important; }
        .markup-input { padding-right: 30px !important; }

        input[type="number"]:focus {
            border-color: var(--focus-border);
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.25);
        }

        .custom-row-action {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
            align-items: flex-end;
            background: #111827;
            padding: 15px;
            border-radius: 8px;
            border: 1px dashed var(--border-color);
        }

        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
            white-space: nowrap;
        }

        .btn-primary {
            background-color: #3b82f6;
            color: #fff;
        }

        .btn-primary:hover {
            background-color: #2563eb;
        }

        .btn-danger {
            background-color: #ef4444;
            color: #fff;
            padding: 6px 12px;
            font-size: 12px;
            border-radius: 4px;
        }

        .btn-danger:hover {
            background-color: #dc2626;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            padding: 14px 12px;
            text-align: right;
        }

        th {
            color: var(--text-muted);
            font-size: 13px;
            font-weight: 600;
            border-bottom: 2px solid var(--table-border);
            vertical-align: bottom;
        }

        th .sub-label {
            display: block;
            font-size: 11px;
            font-weight: normal;
            color: #64748b;
            margin-top: 2px;
        }

        td {
            font-size: 14px;
            border-bottom: 1px solid var(--table-border);
        }

        .col-qty {
            text-align: center;
            font-weight: 500;
            color: var(--text-muted);
        }

        .col-markup { color: #f43f5e; }
        .col-list-price { color: #a855f7; }
        .col-discount-price { color: #fb923c; font-weight: 500; }
        .col-shipping { color: #60a5fa; }
        
        .col-diff-positive { color: #34d399; font-weight: 500; }
        .col-diff-negative { color: #f87171; font-weight: 500; }
        
        .col-total {
            font-weight: 600;
            color: #34d399;
            font-size: 15px;
        }
        
        .col-action {
            text-align: center;
            width: 60px;
        }
    </style>
</head>
<body>

<div class="calculator-card">
    <div class="input-row">
        <div class="input-group">
            <label for="base-price">基础单价</label>
            <div class="input-wrapper">
                <span class="prefix-symbol">¥</span>
                <input type="number" id="base-price" class="price-input" value="1.00" step="0.01" min="0" oninput="calculateTotals()">
            </div>
        </div>
        
        <div class="input-group">
            <label for="price-markup">价格升幅</label>
            <div class="input-wrapper">
                <input type="number" id="price-markup" class="markup-input" value="50.0" step="0.1" oninput="calculateTotals()">
                <span class="suffix-symbol">%</span>
            </div>
        </div>

        <div class="input-group">
            <label for="discount-rate">折扣率</label>
            <div class="input-wrapper">
                <input type="number" id="discount-rate" class="discount-input" value="0.72" step="0.01" min="0" max="1" oninput="calculateTotals()">
            </div>
        </div>

        <div class="input-group">
            <label for="base-shipping">首件运费</label>
            <div class="input-wrapper">
                <span class="prefix-symbol">¥</span>
                <input type="number" id="base-shipping" class="shipping-input" value="4.50" step="0.01" min="0" oninput="calculateTotals()">
            </div>
        </div>

        <div class="input-group">
            <label for="shipping-increment">续件运费递增</label>
            <div class="input-wrapper">
                <span class="prefix-symbol">¥</span>
                <input type="number" id="shipping-increment" class="shipping-inc-input" value="0.10" step="0.01" min="0" oninput="calculateTotals()">
            </div>
        </div>

        <div class="input-group">
            <label for="free-shipping-threshold">包邮门槛</label>
            <div class="input-wrapper">
                <span class="prefix-symbol">¥</span>
                <input type="number" id="free-shipping-threshold" class="free-shipping-input" value="20.00" step="0.01" min="0" oninput="calculateTotals()">
            </div>
        </div>
    </div>

    <div class="custom-row-action">
        <div class="input-group" style="max-width: 200px;">
            <label for="custom-qty">新增自定义数量</label>
            <input type="number" id="custom-qty" min="1" step="1" placeholder="输入数量">
        </div>
        <button class="btn btn-primary" onclick="addCustomRow()">添加行</button>
    </div>
    
    <table>
        <thead>
            <tr>
                <th class="col-qty">数量</th>
                <th>价格升幅</th>
                <th>标价</th>
                <th>折扣价</th>
                <th>发货运费</th>
                <th>
                    差价
                    <span class="sub-label">折扣价 - 单价 - 运费</span>
                </th>
                <th>最终总价格</th>
                <th class="col-action">操作</th>
            </tr>
        </thead>
        <tbody id="table-body">
            <tr class="calc-row" data-qty="1">
                <td class="col-qty">1</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="2">
                <td class="col-qty">2</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="3">
                <td class="col-qty">3</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="4">
                <td class="col-qty">4</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="5">
                <td class="col-qty">5</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="6">
                <td class="col-qty">6</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="7">
                <td class="col-qty">7</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="8">
                <td class="col-qty">8</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="9">
                <td class="col-qty">9</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="10">
                <td class="col-qty">10</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="20">
                <td class="col-qty">20</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="50">
                <td class="col-qty">50</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
            <tr class="calc-row" data-qty="100">
                <td class="col-qty">100</td>
                <td class="col-markup">+0.00</td>
                <td class="col-list-price">¥ 0.00</td>
                <td class="col-discount-price">¥ 0.00</td>
                <td class="col-shipping">¥ 0.00</td>
                <td class="col-diff">¥ 0.00</td>
                <td class="col-total">¥ 0.00</td>
                <td class="col-action"></td>
            </tr>
        </tbody>
    </table>
</div>

<script>
    function calculateTotals() {
        const priceInput = document.getElementById('base-price');
        const markupInput = document.getElementById('price-markup');
        const discountInput = document.getElementById('discount-rate');
        const shippingInput = document.getElementById('base-shipping');
        const shippingIncInput = document.getElementById('shipping-increment');
        const freeShippingInput = document.getElementById('free-shipping-threshold');
        
        const basePrice = parseFloat(priceInput.value) || 0;
        const markupPercent = parseFloat(markupInput.value) || 0;
        const discountRate = parseFloat(discountInput.value) || 0;
        const baseShipping = parseFloat(shippingInput.value) || 0;
        const shippingIncrement = parseFloat(shippingIncInput.value) || 0;
        const freeShippingThreshold = parseFloat(freeShippingInput.value) || 0;

        const rows = document.querySelectorAll('.calc-row');

        rows.forEach(row => {
            const qty = parseInt(row.getAttribute('data-qty'));
            const markupTd = row.querySelector('.col-markup');
            const listPriceTd = row.querySelector('.col-list-price');
            const discountTd = row.querySelector('.col-discount-price');
            const shippingTd = row.querySelector('.col-shipping');
            const diffTd = row.querySelector('.col-diff');
            const totalTd = row.querySelector('.col-total');
            
            const singleMarkup = basePrice * (markupPercent / 100);
            const rowMarkupTotal = singleMarkup * qty;

            const rowListPrice = (basePrice + singleMarkup) * qty;

            const rowDiscountPrice = basePrice * ((markupPercent / 100) + 1) * qty * discountRate;

            let rowShippingTotal = 0;
            if (qty > 0) {
                if (rowListPrice < freeShippingThreshold) {
                    rowShippingTotal = 0;
                } else {
                    rowShippingTotal = baseShipping + qty * shippingIncrement;
                }
            }

            const rowPriceTotal = basePrice * qty;

            const rowDiff = rowDiscountPrice - rowPriceTotal - rowShippingTotal;

            const rowTotal = rowPriceTotal + rowMarkupTotal + rowShippingTotal;

            markupTd.innerText = '+' + rowMarkupTotal.toFixed(2);
            listPriceTd.innerText = '¥ ' + rowListPrice.toFixed(2);
            discountTd.innerText = '¥ ' + rowDiscountPrice.toFixed(2);
            shippingTd.innerText = '¥ ' + rowShippingTotal.toFixed(2);
            
            if (rowDiff >= 0) {
                diffTd.innerText = '¥ ' + rowDiff.toFixed(2);
                diffTd.className = 'col-diff col-diff-positive';
            } else {
                diffTd.innerText = '-¥ ' + Math.abs(rowDiff).toFixed(2);
                diffTd.className = 'col-diff col-diff-negative';
            }
            
            totalTd.innerText = '¥ ' + rowTotal.toFixed(2);
        });
    }

    function addCustomRow() {
        const qtyInput = document.getElementById('custom-qty');
        const qty = parseInt(qtyInput.value);

        if (isNaN(qty) || qty <= 0) {
            alert('请输入有效的数量！');
            return;
        }

        const tbody = document.getElementById('table-body');
        
        // 检查是否已经存在该数量的行
        const existingRows = tbody.querySelectorAll('.calc-row');
        for (let row of existingRows) {
            if (parseInt(row.getAttribute('data-qty')) === qty) {
                alert('该数量的行已存在！');
                return;
            }
        }

        // 创建新行
        const tr = document.createElement('tr');
        tr.className = 'calc-row';
        tr.setAttribute('data-qty', qty);

        tr.innerHTML = `
            <td class="col-qty">${qty}</td>
            <td class="col-markup">+0.00</td>
            <td class="col-list-price">¥ 0.00</td>
            <td class="col-discount-price">¥ 0.00</td>
            <td class="col-shipping">¥ 0.00</td>
            <td class="col-diff">¥ 0.00</td>
            <td class="col-total">¥ 0.00</td>
            <td class="col-action">
                <button class="btn btn-danger" onclick="deleteRow(this)">删除</button>
            </td>
        `;

        // 寻找合适的位置插入，保持数量升序排列
        let inserted = false;
        for (let row of existingRows) {
            const currentQty = parseInt(row.getAttribute('data-qty'));
            if (qty < currentQty) {
                tbody.insertBefore(tr, row);
                inserted = true;
                break;
            }
        }

        if (!inserted) {
            tbody.appendChild(tr);
        }

        qtyInput.value = '';
        calculateTotals();
    }

    function deleteRow(button) {
        const row = button.closest('.calc-row');
        row.remove();
        calculateTotals();
    }

    window.onload = calculateTotals;
</script>

</body>
</html>