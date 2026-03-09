# FreelancerConnect Frontend

A modern Vue.js 3 frontend for the FreelancerConnect platform — connecting talented freelancers with clients.

## ✨ Features

- 🔐 JWT Authentication (Login / Register)
- 👤 Role-based access: CLIENT and FREELANCER
- 📋 Project browsing, creation, and detail views
- 💰 Bid submission and management
- 👥 Freelancer directory with skill search
- 📊 Dashboards for both roles
- 🌙 Dark theme with modern UI

## 🚀 Getting Started

### Prerequisites

- Node.js 18+
- npm 8+
- Backend running at `http://localhost:8765`

### Installation

```bash
cd frontend
npm install
```

### Development Server

```bash
npm run dev
```

Opens at `http://localhost:5173`

### Production Build

```bash
npm run build
```

Output in `dist/`

### Preview Production Build

```bash
npm run preview
```

## 🏗️ Architecture

```
src/
├── api/              # Axios API client + per-domain API functions
├── types/            # TypeScript interfaces (Backend, UI, Modified)
├── mappers/          # Transform data between backend ↔ UI shapes
├── store/            # Vuex store with namespaced modules
│   └── modules/      # auth | client | freelancer | project | bid
├── components/
│   ├── shared/       # AppButton, AppInput, AppCard, AppModal, AppNavbar...
│   ├── project/      # ProjectCard, ProjectList
│   ├── freelancer/   # FreelancerCard
│   └── bid/          # BidCard
├── views/            # Page-level components
├── router/           # Vue Router with navigation guards
└── styles/           # SCSS design system (_colors, _variables, _mixins)
```

## 🎨 Design System

The UI uses a dark navy/slate color palette with indigo/violet primary accents.

Color variables in `src/styles/_colors.scss`:

| Variable | Value | Purpose |
|---|---|---|
| `$color-primary` | `#6366f1` | Primary CTA, links |
| `$color-background` | `#0f172a` | Page background |
| `$color-surface` | `#1e293b` | Card backgrounds |
| `$color-text-primary` | `#f8fafc` | Main text |

## 🔐 Authentication Flow

1. User registers → selects `FREELANCER` or `CLIENT` role
2. User logs in → JWT token received and stored in `localStorage`
3. Token is decoded to extract user info (id, name, email, role)
4. Vuex `auth` store holds token + user state
5. Router guards redirect unauthenticated users to `/login`
6. Role-based guards prevent cross-role access

## 🧩 State Management Pattern

Each Vuex module follows the same three-state pattern:

```typescript
state = {
  originalFromBackend: T | null,   // Raw API response
  UIfromOriginal: TUI | null,      // Mapped for display
  modifiedToBackend: TModified | null,  // Prepared for API write
  isLoading: boolean,
  error: string | null,
}
```

## 🌐 API Configuration

The API base URL defaults to `http://localhost:8765`. To change it, update `src/api/index.ts`:

```typescript
const API_BASE_URL = 'http://localhost:8765';
```

## 📦 Dependencies

| Package | Version | Purpose |
|---|---|---|
| vue | ^3.5 | UI framework |
| vuex | ^4.x | State management |
| vue-router | ^4.x | Client-side routing |
| axios | ^1.x | HTTP client |
| sass | ^1.x | SCSS styling |
| vite | ^7.x | Build tool |
| vue-tsc | ^2.x | TypeScript type checking |
