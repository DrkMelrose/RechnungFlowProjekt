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
    {
        id: 1,
        invoiceNumber: "INV-2026-001",
        client: "Müller Gebäudereinigung",
        issueDate: "2026-04-30",
        dueDate: "2026-05-14",
        amount: 1280.0,
        status: "Paid",
    },
    {
        id: 2,
        invoiceNumber: "INV-2026-002",
        client: "CleanHaus GmbH",
        issueDate: "2026-05-02",
        dueDate: "2026-05-16",
        amount: 2280.0,
        status: "Open",
    },
    {
        id: 3,
        invoiceNumber: "INV-2026-003",
        client: "Schmidt Services",
        issueDate: "2026-05-05",
        dueDate: "2026-05-19",
        amount: 735.0,
        status: "Overdue",
    },
];

export const workLogs = [
    {
        id: 1,
        date: "2026-04-28",
        employee: "Anna Schmidt",
        object: "Bürogebäude West",
        client: "Müller Gebäudereinigung",
        hours: 4.0,
        status: "Approved",
    },
    {
        id: 2,
        date: "2026-04-29",
        employee: "Max Meier",
        object: "Einkaufszentrum City",
        client: "CleanHaus GmbH",
        hours: 6.0,
        status: "Pending",
    },
    {
        id: 3,
        date: "2026-04-30",
        employee: "Thomas Weber",
        object: "Arztpraxis Schneider",
        client: "Schmidt Services",
        hours: 3.5,
        status: "Invoiced",
    },
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

export const clients = [
    {
        id: 1,
        name: "Müller Gebäudereinigung",
        email: "info@mueller.de",
        phone: "+49 151 123456",
        address: "Bonn",
        status: "Active",
    },
    {
        id: 2,
        name: "CleanHaus GmbH",
        email: "kontakt@cleanhaus.de",
        phone: "+49 152 987654",
        address: "Köln",
        status: "Active",
    },
    {
        id: 3,
        name: "Schmidt Services",
        email: "office@schmidt.de",
        phone: "+49 160 555555",
        address: "Düsseldorf",
        status: "Inactive",
    },
];

export const cleaningObjects  = [
    {
        id: 1,
        name: "Bürogebäude West",
        client: "Müller Gebäudereinigung",
        address: "Bonn Zentrum",
        type: "Office",
        hourlyRate: 32,
        status: "Active",
    },
    {
        id: 2,
        name: "Einkaufszentrum City",
        client: "CleanHaus GmbH",
        address: "Köln Innenstadt",
        type: "Commercial",
        hourlyRate: 38,
        status: "Active",
    },
    {
        id: 3,
        name: "Arztpraxis Schneider",
        client: "Schmidt Services",
        address: "Düsseldorf",
        type: "Medical",
        hourlyRate: 35,
        status: "Inactive",
    },
];

export const employees = [
    {
        id: 1,
        firstName: "Max",
        lastName: "Meier",
        email: "max.meier@rechnungflow.de",
        phone: "+49 151 123456",
        position: "Cleaner",
        status: "Active",
    },
    {
        id: 2,
        firstName: "Anna",
        lastName: "Schmidt",
        email: "anna.schmidt@rechnungflow.de",
        phone: "+49 152 654321",
        position: "Team Leader",
        status: "Active",
    },
    {
        id: 3,
        firstName: "Thomas",
        lastName: "Weber",
        email: "thomas.weber@rechnungflow.de",
        phone: "+49 160 777777",
        position: "Cleaner",
        status: "Inactive",
    },
];