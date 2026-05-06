import {
    Home,
    Users,
    Building2,
    UserRound,
    ClipboardList,
    FileText,
    ReceiptText,
    BarChart3,
    Settings
} from "lucide-react";

export const revenueData = [
    { date: "Apr 1", revenue: 900 },
    { date: "Apr 5", revenue: 1400 },
    { date: "Apr 8", revenue: 2300 },
    { date: "Apr 12", revenue: 1800 },
    { date: "Apr 15", revenue: 3900 },
    { date: "Apr 18", revenue: 2900 },
    { date: "Apr 22", revenue: 5000 },
    { date: "Apr 25", revenue: 3600 },
    { date: "Apr 29", revenue: 6700 },
]

export const invoices = [
    { number: "INV-2026-0012", client: "Müller GmbH", amount: "€1,250.00", status: "Paid" },
    { number: "INV-2026-0011", client: "CleanHaus AG", amount: "€980.00", status: "Open" },
    { number: "INV-2026-0010", client: "Schmidt & Co.", amount: "€1,500.00", status: "Paid" },
    { number: "INV-2026-0009", client: "Weber Services", amount: "€720.00", status: "Open" },
    { number: "INV-2026-0008", client: "Hoffmann GmbH", amount: "€1,100.00", status: "Paid" },
]

export const workLogs = [
    { date: "Apr 28, 2026", object: "Bürogebäude West", employee: "Johannes Klein", hours: "4.0 h" },
    { date: "Apr 29, 2026", object: "Einkaufszentrum City", employee: "Anna Meier", hours: "6.0 h" },
]

export const navItems = [
    { label: "Dashboard", icon: Home, active: true },
    { label: "Clients", icon: Users },
    { label: "Objects", icon: Building2 },
    { label: "Employees", icon: UserRound },
    { label: "Work Logs", icon: ClipboardList },
    { label: "Invoices", icon: FileText },
    { label: "Generate Invoice", icon: ReceiptText },
    { label: "Reports", icon: BarChart3 },
    { label: "Settings", icon: Settings },
]

