# ドローン在庫管理システム IF 仕様書（共通）

## 📋 文書概要

| 項目       | 内容                                       |
| ---------- | ------------------------------------------ |
| 文書名     | ドローン在庫管理システム IF 仕様書（共通） |
| 作成日     | 2024 年 12 月 15 日                        |
| 最終更新日 | 2024 年 12 月 15 日                        |
| バージョン | 1.0.0                                      |
| 作成者     | Development Team                           |
| 承認者     | System Architect                           |

---

## 1. システム概要

### 1.1 連携システム概要

ドローン在庫管理システムは以下の外部システムと連携し、リアルタイムでの在庫管理を実現します。

| システム名       | 連携方向 | 主要データ                           | 更新頻度     |
| ---------------- | -------- | ------------------------------------ | ------------ |
| **購買システム** | 双方向   | 部品入荷情報、入荷取消情報、在庫情報 | リアルタイム |
| **生産システム** | 双方向   | 生産指示、製品入庫情報、在庫情報     | リアルタイム |
| **販売システム** | 双方向   | 製品出荷情報、出荷取消情報、在庫情報 | リアルタイム |

### 1.2 設計方針

#### 1.2.1 基本原則

- **REST API**: HTTP/HTTPS ベースの標準的な REST API を採用
- **JSON 形式**: データ交換は JSON 形式を標準とする
- **同期処理**: リアルタイム在庫更新のため同期 API 方式を採用
- **べき等性**: 重複処理への対応（冪等キーによる重複排除）
- **エラーハンドリング**: 標準的な HTTP ステータスコードとエラーレスポンス

#### 1.2.2 API 設計ガイドライン

- **エンドポイント命名**: RESTful な命名規則（複数形リソース名）
- **HTTP メソッド**: GET（参照）、POST（作成）
- **レスポンス統一**: 成功・エラー共に統一されたレスポンス形式

---

## 2. API 一覧

### 2.1 部品在庫管理 API

| API 名           | エンドポイント              | HTTP メソッド | 概要                       | 権限レベル                  |
| ---------------- | --------------------------- | ------------- | -------------------------- | --------------------------- |
| 部品在庫照会 API | `/api/parts/stock`          | GET           | 部品在庫情報の照会         | SYSTEM_BUYER<br>SYSTEM_PROD |
| 部品入荷 API     | `/api/parts/receipt`        | POST          | 購買システムからの部品入荷 | SYSTEM_BUYER                |
| 部品入荷取消 API | `/api/parts/receipt/cancel` | POST          | 購買キャンセル時の入荷取消 | SYSTEM_BUYER                |
| 部品出庫 API     | `/api/parts/issue`          | POST          | 生産システムへの部品出庫   | SYSTEM_PROD                 |
| 部品出庫取消 API | `/api/parts/issue/cancel`   | POST          | 製造計画変更時の出庫取消   | SYSTEM_PROD                 |

### 2.2 製品在庫管理 API

| API 名           | エンドポイント              | HTTP メソッド | 概要                       | 権限レベル                  |
| ---------------- | --------------------------- | ------------- | -------------------------- | --------------------------- |
| 製品在庫照会 API | `/api/products/stock`       | GET           | 製品在庫情報の照会         | SYSTEM_PROD<br>SYSTEM_SALES |
| 製品入荷 API     | `/api/products/receipt`     | POST          | 製造完了後の製品入庫       | SYSTEM_PROD                 |
| 製品出荷 API     | `/api/products/ship`        | POST          | 販売システムへの製品出荷   | SYSTEM_SALES                |
| 製品出荷取消 API | `/api/products/ship/cancel` | POST          | 出荷キャンセル時の在庫復旧 | SYSTEM_SALES                |

---

## 3. 認証・認可

### 3.1 JWT 認証API

#### 3.1.1 認証方式

- **JWT トークン**: JSON Web Token を使用したステートレス認証
- **トークン有効期限**: 24 時間（リフレッシュトークンによる更新対応）
- **システム権限**: JWT ペイロード内にシステム権限情報を含める
- **署名検証**: RS256 アルゴリズムによる電子署名検証

#### 3.1.2 リクエストヘッダー

```http
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

#### 3.1.3 JWT ペイロード構造

```json
{
  "iss": "drone-inventory-system",
  "sub": "PURCHASE_SYSTEM",
  "aud": "api.drone-inventory.local",
  "exp": 1671120000,
  "iat": 1671033600,
  "system_id": "PURCHASE_SYSTEM",
  "permissions": ["SYSTEM_BUYER"],
  "client_id": "purchase-client-001"
}
```

#### 3.1.4 トークン取得 API

**エンドポイント**: `POST /api/auth/token`

**リクエスト**:

```json
{
  "client_id": "purchase-client-001",
  "client_secret": "***",
  "grant_type": "client_credentials",
  "scope": "SYSTEM_BUYER"
}
```

**レスポンス**:

```json
{
  "access_token": "<JWT_TOKEN>",
  "token_type": "Bearer",
  "expires_in": 86400,
  "scope": "SYSTEM_BUYER"
}
```

## 4. 共通仕様

### 4.1 冪等性制御

重複処理防止のため、以下のヘッダーを必須とします：

```http
X-Idempotency-Key: <UNIQUE_KEY>
```

### 4.2 共通リクエストヘッダー

全ての API 呼び出しで必須となるヘッダー：

```http
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
X-Idempotency-Key: <UNIQUE_KEY>
```

### 4.3 共通レスポンス形式

#### 4.3.1 成功レスポンス

```json
{
  "status": "success",
  "message": "処理完了メッセージ",
  "data": {
    // 処理結果データ
  }
}
```

#### 4.3.2 エラーレスポンス

```json
{
  "status": "error",
  "message": "エラーの概要説明",
  "error_code": "ERROR_CODE",
  "details": "詳細なエラー内容",
  "timestamp": "2024-12-15T10:30:15Z"
}
```

### 4.4 エラーコード一覧

| エラーコード         | HTTP ステータス | 説明               |
| -------------------- | --------------- | ------------------ |
| `SUCCESS`            | 200             | 正常処理完了       |
| `INVALID_REQUEST`    | 400             | リクエスト形式不正 |
| `UNAUTHORIZED`       | 401             | 認証エラー         |
| `RESOURCE_NOT_FOUND` | 404             | リソース未存在     |
| `DUPLICATE_REQUEST`  | 409             | 重複リクエスト     |
| `INSUFFICIENT_STOCK` | 422             | 在庫不足           |
| `INTERNAL_ERROR`     | 500             | システム内部エラー |

---

## 5. セキュリティ要件

### 5.1 通信セキュリティ

- **TLS バージョン**: 1.2 以上必須
- **IP 制限**: 許可された外部システム IP からのアクセスのみ
- **入力値検証**: 厳密なバリデーション実装

### 5.2 JWT セキュリティ

- **署名アルゴリズム**: RS256（RSA 署名）
- **トークン有効期限**: 24 時間
- **キーローテーション**: 公開鍵・秘密鍵ペアの定期的な更新
- **トークン検証**: 署名検証、有効期限チェック、権限確認

### 5.3 監査・ログ

- **API 呼び出しログ**: 全リクエストの記録
- **操作履歴**: 在庫変動の詳細ログ
- **セキュリティログ**: 認証失敗・不正アクセス記録
- **JWT トークンログ**: トークン発行・検証・失効の記録

---

## 📋 承認履歴

| バージョン | 更新日     | 更新者           | 承認者           | 更新内容                      |
| ---------- | ---------- | ---------------- | ---------------- | ----------------------------- |
| 1.0.0      | 2024-12-15 | Development Team | System Architect | IF 仕様書共通ドキュメント作成 |

---
